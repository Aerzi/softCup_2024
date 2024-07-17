package com.example.backend.model.request.student.project;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponse {
    private Integer id;

    private String name;

    private String description;

    private Integer nums;

    private Double finishedCondition;

    private Integer classId;

    private Integer userId;
}
