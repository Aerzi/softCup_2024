package com.example.backend.model.request.question;

import com.example.backend.base.BasePage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class QuestionPageRequest extends BasePage {
    private Integer id;

    private String name;

    private String description;

    private String format;

    private String example;

    private String difficult;

    private String tips;

    private Date createTime;

    private Date modifyTime;

    private Integer status;

    @NotNull
    private Integer classId;
}
