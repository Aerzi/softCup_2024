package com.example.backend.model.request.student.project;

import com.example.backend.base.BasePage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectPageRequest extends BasePage {
    private Integer id;

    private String name;

    private String description;

    private Integer nums;

    private Integer finishedCondition;

    private Boolean deleted;

    private Integer classId;

    private Integer userId;
}
