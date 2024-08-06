package com.example.backend.model.entity.aippt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AIPPTResponse {
    private String flag;
    private String code;
    private String desc;
    private String count;
    private AIPPTData data;
}
