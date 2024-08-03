package com.example.backend.service.impl;

import cn.hutool.json.JSONUtil;
import com.example.backend.config.property.SystemConfig;
import com.example.backend.model.request.student.spark.chatdoc.ChatDocApiResponse;
import com.example.backend.service.AIChatDocService;
import com.example.backend.utils.spark.chatdoc.ApiAuthUtil;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class AIChatDocServiceImpl implements AIChatDocService {
    private final SystemConfig systemConfig;

    @Autowired
    public AIChatDocServiceImpl(SystemConfig systemConfig) {
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

}
