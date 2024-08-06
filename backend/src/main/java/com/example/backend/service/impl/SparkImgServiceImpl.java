package com.example.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.backend.config.property.SystemConfig;
import com.example.backend.mapper.FileMapper;
import com.example.backend.model.entity.File;
import com.example.backend.model.request.student.spark.aiimg.AIImgPageRequest;
import com.example.backend.model.request.student.spark.aiimg.AIImgRequest;
import com.example.backend.service.FileUploadService;
import com.example.backend.service.SparkImgService;
import com.example.backend.utils.OkHttpClientUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

import static com.example.backend.utils.spark.sparkimg.AuthUrlUtil.getAuthUrl;

@Service
public class SparkImgServiceImpl implements SparkImgService {
    private final FileUploadService fileUploadService;
    private final FileMapper fileMapper;
    private final SystemConfig systemConfig;

    @Autowired
    public SparkImgServiceImpl(FileUploadService fileUploadService, FileMapper fileMapper, SystemConfig systemConfig) {
        this.fileUploadService = fileUploadService;
        this.fileMapper = fileMapper;
        this.systemConfig = systemConfig;
    }

    public JSONObject createJson(AIImgRequest request) {
        // 构建 header 部分
        JSONObject header = new JSONObject();
        header.put("app_id", systemConfig.getSparkImgConfig().getAppId());
        header.put("uid", UUID.randomUUID().toString().substring(0, 15));

        // 构建 chat 部分
        JSONObject chat = new JSONObject();
        chat.put("domain", "s291394db");
        chat.put("temperature", 0.5);
        chat.put("max_tokens", 4096);
        chat.put("width", request.getWidth());
        chat.put("height", request.getHeight());

        // 构建 parameter 部分
        JSONObject parameter = new JSONObject();
        parameter.put("chat", chat);

        // 构建 message 部分
        JSONArray textArray = new JSONArray();
        JSONObject text = new JSONObject();
        text.put("role", "user");
        text.put("content", request.getContent());
        textArray.add(text);

        JSONObject message = new JSONObject();
        message.put("text", textArray);

        // 构建 payload 部分
        JSONObject payload = new JSONObject();
        payload.put("message", message);

        // 组合所有部分
        JSONObject json = new JSONObject();
        json.put("header", header);
        json.put("parameter", parameter);
        json.put("payload", payload);

        return json;
    }

    @Override
    public String sparkImgGenerate(AIImgRequest request) {
        String authUrl = null;
        try {
            authUrl = getAuthUrl(systemConfig.getSparkImgConfig().getHostUrl(), systemConfig.getSparkImgConfig().getApiKey(), systemConfig.getSparkImgConfig().getApiSecret());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObjectRequest = createJson(request);
        String jsonString = JSON.toJSONString(jsonObjectRequest);

        String res = OkHttpClientUtil.doPostJson(authUrl, null, jsonString);

        JSONObject jsonObject = JSON.parseObject(res);
        JSONObject payload = jsonObject.getJSONObject("payload");
        JSONObject choices = payload.getJSONObject("choices");
        JSONArray textArray = choices.getJSONArray("text");
        JSONObject textObject = textArray.getJSONObject(0);

        // 获取 content 字段
        String content = textObject.getString("content");

        // 解码 base64 数据
        byte[] imageBytes = Base64.getDecoder().decode(content);

        // 将 byte[] 转换为 InputStream
        InputStream inputStream = new ByteArrayInputStream(imageBytes);

        return fileUploadService.uploadFile(inputStream, 0, null);
    }

    @Override
    public PageInfo<File> page(AIImgPageRequest request) {
        LambdaQueryWrapper<File> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(File::getUserId, request.getUserId())
                .eq(File::getDeleted, false)
                .eq(File::getExtension, "png")
                .eq(File::getType, "IMG")
                .eq(File::getIsAiGen, true);

        return PageHelper.startPage(request.getPageIndex(), request.getPageSize(), "id desc").doSelectPageInfo(() ->
                fileMapper.selectList(queryWrapper)
        );
    }
}
