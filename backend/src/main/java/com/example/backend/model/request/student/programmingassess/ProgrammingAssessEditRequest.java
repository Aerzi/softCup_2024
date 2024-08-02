package com.example.backend.model.request.student.programmingassess;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgrammingAssessEditRequest {
    private String feedback;

    private String modifiedCode;

    private String errorAnalysis;

    private String optimizationSuggestions;

    private Integer questionId;

    private Integer userId;
}
