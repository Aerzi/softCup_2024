package com.example.backend.model.request.teacher.aclass;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ClassEditRequest {
    @NotNull
    private Integer id;

    private String name;

    private String description;

    private Integer status;
}
