package com.example.backend.model.request.question;

import com.example.backend.base.BasePage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class QuestionPageRequest extends BasePage {
    private Integer id;

    private String name;

    private String type;

    private String description;

    private String content;

    private Integer score;

    private Integer difficult;

    private String correct;

    private Date createTime;

    private Date modifyTime;

    private Integer status;

    @NotNull
    private Integer classId;
}
