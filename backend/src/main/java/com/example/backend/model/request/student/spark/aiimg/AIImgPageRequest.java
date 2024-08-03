package com.example.backend.model.request.student.spark.aiimg;

import com.example.backend.base.BasePage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AIImgPageRequest extends BasePage {
    @NotNull
    private Integer userId;
}
