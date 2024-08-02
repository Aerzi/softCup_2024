package com.example.backend.model.request.admin.eventlog;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class UserEventLogEditRequest {
    @NotNull
    private Integer id;

    private Integer userId;

    private String userName;

    private String content;

    private Date createTime;
}
