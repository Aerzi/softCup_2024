package com.example.backend.model.request.student.judge;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class JudgeSubmitRequest {
    @NotNull
    private Integer questionId;

    @NotNull
    private Integer classId;

    @NotNull
    private String codeType;

    @NotNull
    private JudgeRequest request;
}
