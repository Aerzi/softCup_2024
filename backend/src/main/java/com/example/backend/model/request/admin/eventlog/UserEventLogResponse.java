package com.example.backend.model.request.admin.eventlog;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserEventLogResponse {
    private Integer id;

    private Integer userId;

    private String userName;

    private String content;

    private String createTime;
}
