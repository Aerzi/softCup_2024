package com.example.backend.service;

import com.example.backend.model.request.student.spark.chatdoc.ChatDocApiResponse;
import com.example.backend.model.request.student.spark.chatdoc.ChatDocChatRequest;

public interface AIChatDocService {
    public ChatDocApiResponse upload(String url, String fileName);
    public void chat(ChatDocChatRequest request);
}
