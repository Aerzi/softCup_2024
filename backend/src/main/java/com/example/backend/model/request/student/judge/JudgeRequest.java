package com.example.backend.model.request.student.judge;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JudgeRequest {
    private String source_code;

    private String language_id;

    private String stdin;

    private String expected_output;
}
