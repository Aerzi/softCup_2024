package com.example.backend.model.request.student.project;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProjectEditRequest {
    @NotNull
    private Integer id;

    private String name;

    private String description;

    @NotNull
    private Integer classId;

    @NotNull
    private Integer userId;
}
