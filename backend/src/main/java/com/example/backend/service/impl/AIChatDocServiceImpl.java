package com.example.backend.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.backend.config.property.SystemConfig;
import com.example.backend.model.entity.chatdoc.ChatDocChatMessage;
import com.example.backend.model.entity.chatdoc.ChatDocRequest;
import com.example.backend.model.entity.chatdoc.ChatDocSummaryQueryResponse;
import com.example.backend.model.request.student.spark.chatdoc.ChatDocApiResponse;
import com.example.backend.model.request.student.spark.chatdoc.ChatDocChatRequest;
import com.example.backend.model.request.student.spark.chatdoc.ChatDocSummaryQueryRequest;
import com.example.backend.service.AIChatDocService;
import com.example.backend.utils.spark.chatdoc.ApiAuthUtil;
import com.example.backend.websocket.ChatDocWebSocketServer;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class AIChatDocServiceImpl implements AIChatDocService {
    private final SystemConfig systemConfig;

    @Autowired
    public AIChatDocServiceImpl(SystemConfig systemConfig, ChatDocWebSocketServer chatDocWebSocketServer) {
        this.systemConfig = systemConfig;
    }

    @Override
    public ChatDocApiResponse upload(String url, String fileName) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("url", url);
//        builder.addFormDataPart("file", file.getName(),
//                RequestBody.create(MediaType.parse("multipart/form-data"), file));
        builder.addFormDataPart("fileName", fileName);
        builder.addFormDataPart("fileType", "wiki");
        RequestBody body = builder.build();
        long ts = System.currentTimeMillis() / 1000;
        Request request = new Request.Builder()
                .url(systemConfig.getAiChatDocConfig().getUploadUrl())
                .post(body)
                .addHeader("appId", systemConfig.getAiChatDocConfig().getAppId())
                .addHeader("timestamp", String.valueOf(ts))
                .addHeader("signature", ApiAuthUtil.getSignature(systemConfig.getAiChatDocConfig().getAppId(), systemConfig.getAiChatDocConfig().getSecret(), ts))
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (Objects.equals(response.code(), 200)) {
                String respBody = response.body().string();
                return JSONUtil.toBean(respBody, ChatDocApiResponse.class);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void chat(ChatDocChatRequest request) {
        ChatDocChatMessage message = new ChatDocChatMessage();
        message.setRole("user");
        message.setContent(request.getQuestion());
        // 请求内容
        ChatDocRequest chatDocChatRequest = ChatDocRequest.builder()
                .fileIds(Collections.singletonList(request.getFileId()))
                .topN(3)
                .messages(Collections.singletonList(message))
                .build();

        // 构造url鉴权
        long ts = System.currentTimeMillis() / 1000;
        String signature = ApiAuthUtil.getSignature(systemConfig.getAiChatDocConfig().getAppId(), systemConfig.getAiChatDocConfig().getSecret(), ts);
        String requestUrl = systemConfig.getAiChatDocConfig().getChatUrl() + "?" + "appId=" + systemConfig.getAiChatDocConfig().getAppId() + "&timestamp=" + ts + "&signature=" + signature;
        // ws
        Request wsRequest = (new Request.Builder()).url(requestUrl).build();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        StringBuffer buffer = new StringBuffer();
        WebSocket webSocket = okHttpClient.newWebSocket(wsRequest, new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosed(webSocket, code, reason);
                webSocket.close(1002, "websocket finish");
                okHttpClient.connectionPool().evictAll();
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
                webSocket.close(1001, "websocket finish");
                okHttpClient.connectionPool().evictAll();
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                System.out.println(text);

                //发送text
                try {
                    ChatDocWebSocketServer.sendInfo(text);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                JSONObject jsonObject = JSONUtil.parseObj(text);
                String content = jsonObject.getStr("content");
                if (StrUtil.isNotEmpty(content)) {
                    buffer.append(content);
                }
                if (Objects.equals(jsonObject.getInt("status"), 2)) {
                    System.out.println("回答内容：" + buffer);
                    webSocket.close(1000, "websocket finish");
                    okHttpClient.connectionPool().evictAll();
                }
            }
        });
        webSocket.send(JSONUtil.toJsonStr(chatDocChatRequest));

    }

    @Override
    public ChatDocSummaryQueryResponse query(ChatDocSummaryQueryRequest queryRequest) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("fileId", queryRequest.getFiledId());

        RequestBody body = builder.build();
        long ts = System.currentTimeMillis() / 1000;
        Request request = new Request.Builder()
                .url(systemConfig.getAiChatDocConfig().getSummaryStartUrl())
                .post(body)
                .addHeader("appId", systemConfig.getAiChatDocConfig().getAppId())
                .addHeader("timestamp", String.valueOf(ts))
                .addHeader("signature", ApiAuthUtil.getSignature(systemConfig.getAiChatDocConfig().getAppId(), systemConfig.getAiChatDocConfig().getSecret(), ts))
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (Objects.equals(response.code(), 200)) {
                String respBody = response.body().string();
//                System.out.println(JSONUtil.toBean(respBody, ChatDocSummaryQueryResponse.class).getDesc());
                return JSONUtil.toBean(respBody, ChatDocSummaryQueryResponse.class);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}
