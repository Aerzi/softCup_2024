package com.example.backend.service;

import com.example.backend.model.request.student.spark.aiimg.AIImgRequest;

public interface SparkImgService {
    public String sparkImgGenerate(AIImgRequest request);
}
