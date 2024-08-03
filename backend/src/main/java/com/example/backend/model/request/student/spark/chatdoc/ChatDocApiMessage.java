package com.example.backend.model.request.student.spark.chatdoc;

import lombok.Data;

/**
 * ResponseMsg
 *
 * @author ydwang16
 * @version 2023/09/06 14:11
 **/
@Data
public class ChatDocApiMessage {
    private boolean flag;
    private int code;
    private String desc;
    private String sid;

    public boolean success() {
        return code == 0;
    }
}
