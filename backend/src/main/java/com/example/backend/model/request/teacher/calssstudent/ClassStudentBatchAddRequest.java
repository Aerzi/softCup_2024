package com.example.backend.model.request.teacher.calssstudent;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ClassStudentBatchAddRequest {
    @NotNull
    private List<ClassStudentAddRequest> requests;
}
