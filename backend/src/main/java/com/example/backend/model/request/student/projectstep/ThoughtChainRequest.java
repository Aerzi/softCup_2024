package com.example.backend.model.request.student.projectstep;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ThoughtChainRequest {
    @NotBlank
    private String description;

    private String assess;

    private Boolean finished;
}
