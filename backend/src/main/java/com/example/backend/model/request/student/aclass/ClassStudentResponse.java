package com.example.backend.model.request.student.aclass;

import com.example.backend.model.entity.Class;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassStudentResponse {
    private Integer studentId;

    private Class aClass;

}
