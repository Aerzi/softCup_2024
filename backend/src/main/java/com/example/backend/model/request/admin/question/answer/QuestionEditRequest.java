package com.example.backend.model.request.admin.question.answer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionEditRequest {
    private Integer id;

    private String answer;

    private Integer score;

    private Integer userId;

    private Integer questionId;

    private Integer classId;
}
