package com.example.backend.model.request.question;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class QuestionEditRequest {
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

    private Integer classId;
}
