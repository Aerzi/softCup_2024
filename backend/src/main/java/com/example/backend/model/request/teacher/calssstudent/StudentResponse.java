package com.example.backend.model.request.teacher.calssstudent;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StudentResponse {
    private Integer id;

    private String userName;

    private Integer age;

    private Integer sex;

    private Date birthDay;

    private Integer status;
}
