package com.example.backend.model.entity.aippt;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AIPPTRequest {
    @NotBlank
    private String sid;
}
