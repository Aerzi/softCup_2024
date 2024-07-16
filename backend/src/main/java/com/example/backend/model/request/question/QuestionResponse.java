package com.example.backend.model.request.question;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class QuestionResponse {
    private Integer id;

    private String name;

    private String type;

    private String description;

    private String content;

    private Integer score;

    private Integer difficult;

    private String correct;

    private String createTime;

    private String modifyTime;

    private Integer status;

    private Integer classId;
}
