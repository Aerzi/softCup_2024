package com.example.backend.model.request.teacher.aclass;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ClassCreateRequest {
    @NotBlank
    private String name;

    private String description;
}
