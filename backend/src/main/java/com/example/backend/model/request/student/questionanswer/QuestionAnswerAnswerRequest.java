package com.example.backend.model.request.student.questionanswer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class QuestionAnswerAnswerRequest {
    private String answer;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer questionId;
}
