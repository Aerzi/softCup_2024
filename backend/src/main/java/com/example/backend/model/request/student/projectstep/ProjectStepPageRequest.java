package com.example.backend.model.request.student.projectstep;

import com.example.backend.base.BasePage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProjectStepPageRequest extends BasePage {
    private Integer id;

    private String description;

    private String assess;

    private Boolean finished;

    @NotNull
    private Integer projId;
}
