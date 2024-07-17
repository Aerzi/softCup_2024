package com.example.backend.model.request.student.judge;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class JudgeRequest {
    @NotBlank
    private String source_code;

    @NotBlank
    private String language_id;

    private String stdin;

    private String expected_output;
}
