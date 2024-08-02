package com.example.backend.model.entity.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgrammingAssessResult {
    private String feedback;

    private String modified_code;

    private String error_analysis;

    private String optimization_suggestions;
}
