package com.example.backend.model.request.student.spark.aiimg;

import com.example.backend.base.BasePage;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AIImgResponse extends BasePage {
    private Integer id;

    private String name;

    private String extension;

    private String type;

    private String filePath;

    private Date createTime;

    private Date modifyTime;

    private Integer status;

    private String description;

    private Integer userId;

    private Boolean isAiGen;
}
