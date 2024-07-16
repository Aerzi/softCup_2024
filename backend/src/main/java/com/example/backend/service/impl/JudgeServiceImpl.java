package com.example.backend.service.impl;

import com.example.backend.base.RestResponse;
import com.example.backend.base.SystemCode;
import com.example.backend.config.property.SystemConfig;
import com.example.backend.model.entity.JudgeToken;
import com.example.backend.service.JudgeService;
import com.example.backend.utils.JsonUtil;
import com.example.backend.utils.OkHttpClientUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class JudgeServiceImpl implements JudgeService {
    private final SystemConfig systemConfig;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    public JudgeServiceImpl(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    @Override
    public String createSubmission(String json) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-RapidAPI-Key", systemConfig.getJudge0CreateConfig().getXRapidAPIKey());
        headers.put("X-RapidAPI-Host", systemConfig.getJudge0CreateConfig().getXRapidAPIHost());
        headers.put("content-type", "application/json");
        headers.put("Content-Type", "application/json");

        String token = null;
        try {
            String responseBody = OkHttpClientUtil.doPostJson(systemConfig.getJudge0CreateConfig().getUrl(), json, headers);
            JudgeToken judgeToken = JsonUtil.toJsonObject(responseBody,JudgeToken.class);
            token = judgeToken.getToken();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return token;
    }

    @Override
    public RestResponse<String> getJudgeResult(String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-RapidAPI-Key", systemConfig.getJudge0GetConfig().getXRapidAPIKey());
        headers.put("X-RapidAPI-Host", systemConfig.getJudge0GetConfig().getXRapidAPIHost());
        String url = systemConfig.getJudge0GetConfig().getUrlFront() + token + systemConfig.getJudge0GetConfig().getUrlBack();

        String responseBody = OkHttpClientUtil.doGet(url,null,headers);
        try {
        // 将 JSON 字符串解析为 JsonNode 对象
        JsonNode jsonNode = MAPPER.readTree(responseBody);

        // 在此处处理 JSON 数据
        ((ObjectNode) jsonNode).put("processed", true);

        // 将处理后的 JsonNode 对象转换为 JSON 字符串
        String processedJson = MAPPER.writeValueAsString(jsonNode);

        return RestResponse.ok(processedJson);
        } catch (IOException e) {
            e.printStackTrace();
            return RestResponse.fail(SystemCode.InnerError.getCode(),SystemCode.InnerError.getMessage());
        }
    }

    @Override
    public RestResponse<String> getJudgeStatus() {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-RapidAPI-Key", systemConfig.getJudge0StatusConfig().getXRapidAPIKey());
        headers.put("X-RapidAPI-Host", systemConfig.getJudge0StatusConfig().getXRapidAPIHost());
        String url = systemConfig.getJudge0StatusConfig().getUrl();

        String responseBody = OkHttpClientUtil.doGet(url,null,headers);
        return RestResponse.ok(responseBody);
    }

    @Override
    public RestResponse<String> getJudgeLanguage() {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-RapidAPI-Key", systemConfig.getJudge0LangConfig().getXRapidAPIKey());
        headers.put("X-RapidAPI-Host", systemConfig.getJudge0LangConfig().getXRapidAPIHost());
        String url = systemConfig.getJudge0LangConfig().getUrl();

        String responseBody = OkHttpClientUtil.doGet(url,null,headers);
        return RestResponse.ok(responseBody);
    }
}
