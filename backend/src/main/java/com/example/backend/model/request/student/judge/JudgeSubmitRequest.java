package com.example.backend.model.request.student.judge;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class JudgeSubmitRequest {
    @NotNull
    private Integer userId;

    @NotNull
    private Integer questionId;

    private JudgeRequest request;
}
