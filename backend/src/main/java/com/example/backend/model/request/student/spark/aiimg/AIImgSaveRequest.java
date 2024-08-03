package com.example.backend.model.request.student.spark.aiimg;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AIImgSaveRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String filePath;

    private String description;
}
