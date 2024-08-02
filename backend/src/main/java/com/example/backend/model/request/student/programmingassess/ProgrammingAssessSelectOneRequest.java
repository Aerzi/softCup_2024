package com.example.backend.model.request.student.programmingassess;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProgrammingAssessSelectOneRequest {
    @NotNull
    private Integer questionId;

    @NotNull
    private Integer userId;
}
