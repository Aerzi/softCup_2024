package com.example.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.config.property.SystemConfig;
import com.example.backend.model.entity.airewrite.AIRewriteResponse;
import com.example.backend.model.request.student.spark.rewrite.AIRewriteRequest;
import com.example.backend.model.request.student.spark.rewrite.AIRewriteResultResponse;
import com.example.backend.service.AIRewriteService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AIRewriteServiceImpl implements AIRewriteService {
    private final SystemConfig systemConfig;

    private static final Gson gson = new Gson();

    public Map<Integer, String> initMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "<L1>");
        map.put(2, "<L2>");
        map.put(3, "<L3>");
        map.put(4, "<L4>");
        map.put(5, "<L5>");
        map.put(6, "<L6>");
        return map;
    }

    /**
     * 处理请求URL
     * 封装鉴权参数等
     *
     * @return 处理后的URL
     */
    public String buildRequetUrl() {
        URL url;
        // 替换调schema前缀 ，原因是URL库不支持解析包含ws,wss schema的url
        String httpRequestUrl = systemConfig.getAiRewriteConfig().getRequestUrl().replace("ws://", "http://").replace("wss://", "https://");
        try {
            url = new URL(httpRequestUrl);
            //获取当前日期并格式化
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = format.format(new Date());
            String host = url.getHost();
            if (url.getPort() != 80 && url.getPort() != 443) {
                host = host + ":" + url.getPort();
            }
            String builder = "host: " + host + "\n" +
                    "date: " + date + "\n" +
                    "POST " + url.getPath() + " HTTP/1.1";
            Charset charset = StandardCharsets.UTF_8;
            Mac mac = Mac.getInstance("hmacsha256");
            SecretKeySpec spec = new SecretKeySpec(systemConfig.getAiRewriteConfig().getApiSecret().getBytes(charset), "hmacsha256");
            mac.init(spec);
            byte[] hexDigits = mac.doFinal(builder.getBytes(charset));
            String sha = Base64.getEncoder().encodeToString(hexDigits);

            String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", systemConfig.getAiRewriteConfig().getApiKey(), "hmac-sha256", "host date request-line", sha);
            String authBase = Base64.getEncoder().encodeToString(authorization.getBytes(charset));
            return String.format("%s?authorization=%s&host=%s&date=%s", systemConfig.getAiRewriteConfig().getRequestUrl(), URLEncoder.encode(authBase), URLEncoder.encode(host), URLEncoder.encode(date));

        } catch (Exception e) {
            throw new RuntimeException("assemble requestUrl error:" + e.getMessage());
        }
    }

    /**
     * 组装请求参数
     * 直接使用示例参数，
     * 替换部分值
     *
     * @return 参数字符串
     */
    private String buildParam(AIRewriteRequest request) {
        Map<Integer, String> map = initMap();

        JSONObject json = new JSONObject();

        // Header部分
        JSONObject header = new JSONObject();
        header.put("app_id", systemConfig.getAiRewriteConfig().getAppId());
        header.put("status", 3);
        json.put("header", header);

        // Parameter部分
        JSONObject parameter = new JSONObject();
        JSONObject se3acbe7f = new JSONObject();
        se3acbe7f.put("level", map.get(request.getLevel()));

        JSONObject result = new JSONObject();
        result.put("encoding", "utf8");
        result.put("compress", "raw");
        result.put("format", "json");
        se3acbe7f.put("result", result);

        parameter.put("se3acbe7f", se3acbe7f);
        json.put("parameter", parameter);

        // Payload部分
        JSONObject payload = new JSONObject();
        JSONObject input1 = new JSONObject();
        input1.put("encoding", "utf8");
        input1.put("compress", "raw");
        input1.put("format", "plain");
        input1.put("status", 3);
        input1.put("text", Base64.getEncoder().encodeToString(request.getText().getBytes(StandardCharsets.UTF_8)));

        payload.put("input1", input1);
        json.put("payload", payload);

        return json.toJSONString();
    }

    /**
     * 读取流数据
     *
     * @param is 流
     * @return 字符串
     * @throws IOException 异常
     */
    private String readAllBytes(InputStream is) throws IOException {
        byte[] b = new byte[1024];
        StringBuilder sb = new StringBuilder();
        int len;
        while ((len = is.read(b)) != -1) {
            sb.append(new String(b, 0, len, StandardCharsets.UTF_8));
        }
        return sb.toString();
    }

    /**
     * 请求主方法
     *
     * @return 返回服务结果
     * @throws Exception 异常
     */
    public String doRequest(AIRewriteRequest request) throws Exception {
        URL realUrl = new URL(buildRequetUrl());
        URLConnection connection = realUrl.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-type", "application/json");

        OutputStream out = httpURLConnection.getOutputStream();
        String params = buildParam(request);
        // System.out.println("params=>"+params);
        out.write(params.getBytes(StandardCharsets.UTF_8));
        out.flush();
        InputStream is;
        try {
            is = httpURLConnection.getInputStream();
        } catch (Exception e) {
            is = httpURLConnection.getErrorStream();
            throw new Exception("make request error:" + "code is " + httpURLConnection.getResponseMessage() + readAllBytes(is));
        }
        return readAllBytes(is);
    }

    @Autowired
    public AIRewriteServiceImpl(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    @Override
    public AIRewriteResultResponse rewrite(AIRewriteRequest request) {
        String textBase64Decode = null;
        AIRewriteResultResponse rewriteResultResponse = new AIRewriteResultResponse();
        try {
            String resp = doRequest(request);
            // JSONObject tempJSONObject= JSON.parseObject(resp);
            // System.out.println("文本改写返回的结果："+tempJSONObject);
            AIRewriteResponse response = gson.fromJson(resp, AIRewriteResponse.class);
            textBase64Decode = new String(Base64.getDecoder().decode(response.getPayload().getResult().getText()), StandardCharsets.UTF_8);
            // 返回改写后的文本，和改写文本对应的位置信息
            // System.out.println("text字段Base64解码后=>" + textBase64Decode);

            // 解析JSON数据
            JSONArray jsonArray = JSON.parseArray(textBase64Decode);
            JSONArray innerArray = jsonArray.getJSONArray(0);

            // 提取文本
            String text = innerArray.getString(0);

            // 提取坐标对
            JSONArray coordinatesArray = innerArray.getJSONArray(1);
            List<int[]> coordinates = new ArrayList<>();
            for (int i = 0; i < coordinatesArray.size(); i++) {
                JSONArray pair = coordinatesArray.getJSONArray(i);
                int start = pair.getIntValue(0);
                int end = pair.getIntValue(1);
                coordinates.add(new int[]{start, end});
            }

            // 创建TextData对象
            rewriteResultResponse.setText(text);
            rewriteResultResponse.setCoordinates(coordinates);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rewriteResultResponse;
    }
}
