package com.example.backend.model.request.admin.question.answer;

import com.example.backend.base.BasePage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionAnswerPageRequest extends BasePage {
    private Integer id;

    private String answer;

    private Integer score;

    private Integer userId;

    private Integer questionId;

    private Integer classId;
}
