package com.example.backend.model.request.student.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterRequest {
    @NotBlank
    private String userName;

    @NotBlank
    private String password;

}
