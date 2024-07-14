package com.example.backend.model.request.teacher.calssstudent;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ClassStudentAddRequest {
    @NotNull
    private Integer userId;

    @NotNull
    private Integer classId;
}
