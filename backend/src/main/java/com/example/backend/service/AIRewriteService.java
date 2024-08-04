package com.example.backend.service;

import com.example.backend.model.request.student.spark.rewrite.AIRewriteRequest;
import com.example.backend.model.request.student.spark.rewrite.AIRewriteResultResponse;

public interface AIRewriteService {
    public AIRewriteResultResponse rewrite(AIRewriteRequest request);
}
