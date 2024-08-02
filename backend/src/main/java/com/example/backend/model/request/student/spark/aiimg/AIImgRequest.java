package com.example.backend.model.request.student.spark.aiimg;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AIImgRequest {
    @NotNull
    private Integer width;

    @NotNull
    private Integer height;

    @NotBlank
    private String content;
}
