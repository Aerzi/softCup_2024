package com.example.backend.model.request.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserEditRequest {

    private String userName;

    private String password;

    private String realName;

    private Integer age;

    private Integer sex;

    private Date birthDay;

    private String phone;

    private Integer status;

    private Boolean deleted;
}
