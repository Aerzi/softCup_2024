package com.example.backend.model.request.admin.eventlog;

import com.example.backend.base.BasePage;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserEventLogPageRequest extends BasePage {
    private Integer userId;

    private String userName;

    private String content;
}
