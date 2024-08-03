package com.example.backend.service;

import com.example.backend.model.request.student.spark.chatdoc.ChatDocApiResponse;

public interface AIChatDocService {
    public ChatDocApiResponse upload(String url, String fileName);
}
