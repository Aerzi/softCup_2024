package com.example.backend.model.request.teacher.calss;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class ClassRequest {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Integer userId;
}
