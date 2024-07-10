package com.example.backend.model.request.student.file;

import com.example.backend.base.BasePage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class FilePageRequest extends BasePage {
    private Integer id;

    private String name;

    private String extension;

    @NotBlank
    private String type;

    private Date createTime;

    private Integer status;

    private String description;

    private String userId;
}
