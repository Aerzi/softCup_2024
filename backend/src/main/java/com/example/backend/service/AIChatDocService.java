package com.example.backend.service;

import com.example.backend.model.entity.chatdoc.ChatDocSummaryQueryResponse;
import com.example.backend.model.request.student.spark.chatdoc.ChatDocApiResponse;
import com.example.backend.model.request.student.spark.chatdoc.ChatDocChatRequest;
import com.example.backend.model.request.student.spark.chatdoc.ChatDocSummaryQueryRequest;

public interface AIChatDocService {
    public ChatDocApiResponse upload(String url, String fileName);
    public void chat(ChatDocChatRequest request);
    public ChatDocSummaryQueryResponse query(ChatDocSummaryQueryRequest queryRequest);
}
