package com.example.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.config.property.SystemConfig;
import com.example.backend.model.entity.aippt.*;
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
    private static final String MEDIA_TYPE_JSON = "application/json; charset=utf-8";
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

        validateParameters(systemConfig.getAipptConfig().getAppId(), ts, signature);

        Request request = buildGetRequest(systemConfig.getAipptConfig().getBaseUrl() + "/api/aippt/themeList", ts, signature);

        String response = null;
        try {
            response = executeRequest(request);
            System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return JSON.parseObject(response, AIPPTTemplate.class);
    }

    @Override
    public AIPPTOutlineResponse outline(AIPPTOutlineRequest request) {
        long timestamp = System.currentTimeMillis() / 1000;
        String ts = String.valueOf(timestamp);
        ApiAuthAlgorithm auth = new ApiAuthAlgorithm();
        String signature = auth.getSignature(systemConfig.getAipptConfig().getAppId(), systemConfig.getAipptConfig().getSecret(), timestamp);

        validateParameters(systemConfig.getAipptConfig().getAppId(), ts, signature);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", request.getQuery());
        jsonObject.put("create_model", request.getCreate_model() != null ? request.getCreate_model() : "auto");
        jsonObject.put("theme", request.getTheme() != null ? request.getTheme() : "auto");
        jsonObject.put("business_id", request.getBusiness_id() != null ? request.getBusiness_id() : "my business_id");
        jsonObject.put("author", request.getAuthor() != null ? request.getAuthor() : "智讯课堂");
        jsonObject.put("is_card_note", request.getIs_card_note() != null ? request.getIs_card_note() : false); // boolean default value
        jsonObject.put("is_cover_img", request.getIs_cover_img() != null ? request.getIs_cover_img() : true); // boolean default value
        jsonObject.put("language", request.getLanguage() != null ? request.getLanguage() : "cn");
        jsonObject.put("is_figure", request.getIs_figure() != null ? request.getIs_figure() : false); // boolean default value

        RequestBody body = RequestBody.create(MediaType.get(MEDIA_TYPE_JSON), jsonObject.toString());

        Request Secrequest = buildPostRequest(systemConfig.getAipptConfig().getBaseUrl() + "/api/aippt/createOutline", ts, signature, body);

        String response = null;
        try {
            response = executeRequest(Secrequest);
            System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return JSON.parseObject(response, AIPPTOutlineResponse.class);
    }

    @Override
    public AIPPTOutlineResponse outlineByDoc(AIPPTOutlineByDocRequest request, String file_url, String file_name) {
        long timestamp = System.currentTimeMillis() / 1000;
        String ts = String.valueOf(timestamp);
        ApiAuthAlgorithm auth = new ApiAuthAlgorithm();
        String signature = auth.getSignature(systemConfig.getAipptConfig().getAppId(), systemConfig.getAipptConfig().getSecret(), timestamp);

        validateParameters(systemConfig.getAipptConfig().getAppId(), ts, signature);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("query", request.getQuery() != null ? request.getQuery()  : "");
        builder.addFormDataPart("file_url", file_url);
        builder.addFormDataPart("file_name", file_name);
        builder.addFormDataPart("theme", request.getTheme() != null ? request.getTheme() : "auto");
        builder.addFormDataPart("business_id", request.getBusiness_id() != null ? request.getBusiness_id() : "my business_id");
        builder.addFormDataPart("author", request.getAuthor() != null ? request.getAuthor() : "智讯课堂");
        builder.addFormDataPart("is_card_note", String.valueOf(request.getIs_card_note() != null ? request.getIs_card_note() : false));
        builder.addFormDataPart("is_cover_img", String.valueOf(request.getIs_cover_img() != null ? request.getIs_cover_img() : true));
        builder.addFormDataPart("language", request.getLanguage() != null ? request.getLanguage() : "cn");
        builder.addFormDataPart("is_figure", String.valueOf(request.getIs_figure() != null ? request.getIs_figure() : false));

        MultipartBody body = builder.build();

        Request Secrequest = buildPostRequest(systemConfig.getAipptConfig().getBaseUrl() + "/api/aippt/createOutlineByDoc", ts, signature, body);

        String response = null;
        try {
            response = executeRequest(Secrequest);
            System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return JSON.parseObject(response, AIPPTOutlineResponse.class);
    }

    @Override
    public AIPPTResponse pptByDoc(AIPPTByDocRequest request, String file_url, String file_name) {
        long timestamp = System.currentTimeMillis() / 1000;
        String ts = String.valueOf(timestamp);
        ApiAuthAlgorithm auth = new ApiAuthAlgorithm();
        String signature = auth.getSignature(systemConfig.getAipptConfig().getAppId(), systemConfig.getAipptConfig().getSecret(), timestamp);

        validateParameters(systemConfig.getAipptConfig().getAppId(), ts, signature);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("query", request.getQuery() != null ? request.getQuery()  : "");
        builder.addFormDataPart("file_url", file_url);
        builder.addFormDataPart("file_name", file_name);
        builder.addFormDataPart("theme", request.getTheme() != null ? request.getTheme() : "auto");
        builder.addFormDataPart("business_id", request.getBusiness_id() != null ? request.getBusiness_id() : "my business_id");
        builder.addFormDataPart("author", request.getAuthor() != null ? request.getAuthor() : "智讯课堂");
        builder.addFormDataPart("is_card_note", String.valueOf(request.getIs_card_note() != null ? request.getIs_card_note() : false));
        builder.addFormDataPart("is_cover_img", String.valueOf(request.getIs_cover_img() != null ? request.getIs_cover_img() : true));
        builder.addFormDataPart("language", request.getLanguage() != null ? request.getLanguage() : "cn");
        builder.addFormDataPart("is_figure", String.valueOf(request.getIs_figure() != null ? request.getIs_figure() : false));

        MultipartBody body = builder.build();

        Request Secrequest = buildPostRequest(systemConfig.getAipptConfig().getBaseUrl() + "/api/aippt/createByDoc", ts, signature, body);

        String response = null;
        try {
            response = executeRequest(Secrequest);
            System.out.println(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return JSON.parseObject(response, AIPPTResponse.class);

    }

    public void validateParameters(String... params) {
        for (String param : params) {
            if (param == null || param.isEmpty()) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
        }
    }
}
