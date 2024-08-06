package com.example.backend.service;

import com.example.backend.model.entity.File;
import com.example.backend.model.request.student.spark.aiimg.AIImgPageRequest;
import com.example.backend.model.request.student.spark.aiimg.AIImgRequest;
import com.github.pagehelper.PageInfo;

public interface SparkImgService {
    public String sparkImgGenerate(AIImgRequest request);
    public PageInfo<File> page(AIImgPageRequest request);
}
