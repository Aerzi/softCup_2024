package com.example.backend.model.entity.chatdoc;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatDocSummaryQueryResponse {
    private Boolean flag;

    private Integer code;

    private String sid;

    private String desc;

    private ChatDocSummaryQueryData data;

}
