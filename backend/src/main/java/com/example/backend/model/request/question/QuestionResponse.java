package com.example.backend.model.request.question;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class QuestionResponse {
    private Integer id;

    private String name;

    private String description;

    private String format;

    private String example;

    private String difficult;

    private String tips;

    private String createTime;

    private String modifyTime;

    private Integer classId;
}
