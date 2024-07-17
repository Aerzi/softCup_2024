package com.example.backend.model.request.student.projectstep;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectStepResponse {
    private Integer id;

    private String description;

    private String assess;

    private Boolean finished;

    private Integer projId;
}
