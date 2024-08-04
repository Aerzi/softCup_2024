package com.example.backend.model.entity.aippt;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AIPPTTemplate {
    private Boolean flag;

    private Integer code;

    private String desc;

    private String count;

    private List<AIPPTTemplateData> data;
}
