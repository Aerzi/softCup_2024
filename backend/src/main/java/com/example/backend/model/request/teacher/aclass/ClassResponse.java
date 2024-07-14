package com.example.backend.model.request.teacher.aclass;

import lombok.Getter;
import lombok.Setter;

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
