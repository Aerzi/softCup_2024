package com.example.backend.model.request.student.projectstep;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ProjectStepsAddRequest {
    private List<ThoughtChainRequest> requests;

    @NotNull
    private Integer projId;
}
