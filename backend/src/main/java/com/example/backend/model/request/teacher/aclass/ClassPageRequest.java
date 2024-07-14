package com.example.backend.model.request.teacher.aclass;

import com.example.backend.base.BasePage;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class ClassPageRequest extends BasePage {

    private Integer id;

    private String name;

    private String description;

    private Date createTime;

    private Date modifyTime;

    private Integer status;

    private Integer userId;
}
