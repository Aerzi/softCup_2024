package com.example.backend.model.request.student.aclass;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ClassStudentExitRequest {
    @NotNull
    private Integer userId;

    @NotNull
    private Integer classId;
}
