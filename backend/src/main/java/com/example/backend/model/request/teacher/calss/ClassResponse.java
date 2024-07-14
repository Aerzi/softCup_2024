package com.example.backend.model.request.teacher.calss;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ClassResponse {
    private Integer id;

    private String name;

    private String description;

    private String createTime;

    private String modifyTime;

    private Integer status;

    private Integer userId;
}
