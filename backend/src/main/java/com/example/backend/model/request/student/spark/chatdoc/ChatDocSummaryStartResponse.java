package com.example.backend.model.request.student.spark.chatdoc;

import com.example.backend.model.entity.chatdoc.ChatDocSummaryQueryData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatDocSummaryStartResponse {
    private Boolean flag;

    private Integer code;

    private String sid;

    private String desc;

    private String data;
}
