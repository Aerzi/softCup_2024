package com.example.backend.model.request.teacher.calssstudent;

import com.example.backend.base.BasePage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ClassStudentPageRequest extends BasePage {
    @NotNull
    private Integer classId;
}
