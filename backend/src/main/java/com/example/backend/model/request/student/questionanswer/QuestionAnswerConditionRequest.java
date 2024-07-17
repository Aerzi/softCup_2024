package com.example.backend.model.request.student.questionanswer;

import com.example.backend.base.BasePage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class QuestionAnswerConditionRequest extends BasePage {
    @NotNull
    private Integer userId;

    @NotNull
    private Integer questionId;

    @NotNull
    private Integer classId;
}
