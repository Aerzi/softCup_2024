package com.example.backend.model.request.file;

import com.example.backend.model.request.BaseRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class FileResponse extends BaseRequest {

    private Integer id;

    private String name;

    private String extension;

    private String type;

    private String filePath;

    private String createTime;

    private String modifyTime;

    private Integer size;

    private Integer status;

    private String description;

    private Integer userId;
}
