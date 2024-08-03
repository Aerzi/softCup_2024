package com.example.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.backend.config.property.SystemConfig;
import com.example.backend.model.entity.aippt.AIPPTTemplate;
import com.example.backend.service.AIPPTService;
import com.example.backend.utils.spark.ApiAuthAlgorithm;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class AIPPTServiceImpl implements AIPPTService {
    private static final String ERROR_MESSAGE = "Unexpected code: ";
    private final SystemConfig systemConfig;
    private final static OkHttpClient client = new OkHttpClient().newBuilder()
            .connectionPool(new ConnectionPool(100, 5, TimeUnit.MINUTES))
            .readTimeout(60 * 10, TimeUnit.SECONDS)
            .build();

    @Autowired
    public AIPPTServiceImpl(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    private String getSignature() {
        long timestamp = System.currentTimeMillis() / 1000;
        String ts = String.valueOf(timestamp);
        ApiAuthAlgorithm auth = new ApiAuthAlgorithm();
        return auth.getSignature(systemConfig.getAipptConfig().getAppId(), systemConfig.getAipptConfig().getSecret(), timestamp);
    }

    private Request buildGetRequest(String url, String timestamp, String signature) {
        return new Request.Builder()
                .url(url)
                .addHeader("appId", systemConfig.getAipptConfig().getAppId())
                .addHeader("timestamp", timestamp)
                .addHeader("signature", signature)
                .get()
                .build();
    }

    private Request buildPostRequest(String url, String timestamp, String signature, RequestBody body) {
        return new Request.Builder()
                .url(url)
                .addHeader("appId", systemConfig.getAipptConfig().getAppId())
                .addHeader("timestamp", timestamp)
                .addHeader("signature", signature)
                .post(body)
                .build();
    }

    private String executeRequest(Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println(response.body().string());
                throw new IOException(ERROR_MESSAGE + response);
            }
            return response.body().string();
        }
    }

    @Override
    public AIPPTTemplate getTemplateList() {
        // 获得鉴权信息
        long timestamp = System.currentTimeMillis() / 1000;
        String ts = String.valueOf(timestamp);
        ApiAuthAlgorithm auth = new ApiAuthAlgorithm();
        String signature = auth.getSignature(systemConfig.getAipptConfig().getAppId(), systemConfig.getAipptConfig().getSecret(), timestamp);

        Request request = buildGetRequest(systemConfig.getAipptConfig().getBaseUrl() + "/api/aippt/themeList", ts, signature);

        String response = null;
        try {
            response =  executeRequest(request);
            System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return JSON.parseObject(response,AIPPTTemplate.class);
    }

    public void validateParameters(String... params) {
        for (String param : params) {
            if (param == null || param.isEmpty()) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
        }
    }
}
