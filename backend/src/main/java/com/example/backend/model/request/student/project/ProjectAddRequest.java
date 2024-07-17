package com.example.backend.model.request.student.project;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProjectAddRequest {
    private Integer id;

    @NotBlank
    private String name;

    private String description;

    private Integer nums;

    private Integer finishedCondition;

    @NotNull
    private Integer classId;

    @NotNull
    private Integer userId;
}
