package com.example.backend.model.request;

import lombok.Data;

@Data
public class TeacherRequest {
    private Integer id;

    private String name;

    private String password;
}
