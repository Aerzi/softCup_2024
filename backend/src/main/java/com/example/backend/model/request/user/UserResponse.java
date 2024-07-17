package com.example.backend.model.request.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserResponse {
    private Integer id;

    private String userUuid;

    private String userName;

    private String realName;

    private Integer age;

    private Integer sex;

    private Date birthDay;

    private String phone;

    private Integer gradeLevel;

    private String major;

    private Integer status;

    private String imagePath;

    private String createTime;

    private String modifyTime;

    private String lastActiveTime;
}
