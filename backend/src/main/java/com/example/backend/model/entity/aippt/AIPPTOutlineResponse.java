package com.example.backend.model.entity.aippt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AIPPTOutlineResponse {
    private boolean flag;
    private int code;
    private String desc;
    private Integer count;
    private AIPPTOutlineData data;
}
