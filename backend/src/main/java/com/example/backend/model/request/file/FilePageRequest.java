package com.example.backend.model.request.file;

import com.example.backend.base.BasePage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class FilePageRequest extends BasePage {
    private Integer id;

    private String name;

    private String extension;

    private String type;

    private Integer status;

    private String description;

    @NotNull
    private Integer classId;

    private String userId;
}
