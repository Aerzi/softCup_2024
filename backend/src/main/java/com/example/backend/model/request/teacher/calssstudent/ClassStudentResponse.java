package com.example.backend.model.request.teacher.calssstudent;

import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassStudentResponse {
    private PageInfo<StudentResponse> students;

    private Integer classId;
}
