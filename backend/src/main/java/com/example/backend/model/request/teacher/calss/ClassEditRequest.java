package com.example.backend.model.request.teacher.calss;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
@Getter
@Setter
public class ClassEditRequest {
    @NotNull
    private Integer id;

    private String name;

    private String description;

    private Integer status;

    private Integer userId;
}
