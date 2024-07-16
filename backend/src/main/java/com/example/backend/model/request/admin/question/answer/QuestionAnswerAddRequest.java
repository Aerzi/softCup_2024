package com.example.backend.model.request.admin.question.answer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class QuestionAnswerAddRequest {
    private Integer id;

    @NotBlank
    private String answer;

    @NotNull
    private Integer score;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer questionId;
}
