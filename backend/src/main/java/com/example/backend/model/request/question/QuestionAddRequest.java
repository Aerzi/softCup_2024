package com.example.backend.model.request.question;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class QuestionAddRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String type;

    private String description;

    @NotBlank
    private String content;

    private Integer score;

    private Integer difficult;

    @NotBlank
    private String correct;

    private Date createTime;

    private Date modifyTime;

    @NotNull
    private Integer classId;
}