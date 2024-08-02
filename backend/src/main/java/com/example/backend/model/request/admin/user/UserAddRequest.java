package com.example.backend.model.request.admin.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class UserAddRequest {
    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    private String realName;

    private Integer age;

    private Integer sex;

    private Date birthDay;

    private String phone;

    @NotNull
    private Integer role;

    private Integer gradeLevel;

    private String major;

    private Integer status;
}
