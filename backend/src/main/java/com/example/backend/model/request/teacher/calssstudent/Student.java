package com.example.backend.model.request.teacher.calssstudent;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Student {
    private Integer id;

    private String userUuid;

    private String userName;

    private String realName;

    private Integer age;

    private Integer sex;

    private Date birthDay;

    private String phone;

    private Integer status;

    private String createTime;

    private String modifyTime;
}
