package com.example.backend.model.request.file;

import com.example.backend.model.request.BaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class FileEditRequest {
    @NotNull
    private Integer id;

    private String name;

    private String extension;

    private String type;

    private String filePath;

    private Integer size;

    private Integer status;

    private String description;

    private Integer classId;

    private Integer userId;
}
