package com.example.backend.model.request.student.spark.chatdoc;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatDocApiResponse extends ChatDocApiMessage{
    private Datas data;

    @Data
    public static class Datas{
        private String originPath;
        private String filePath;
        private String fileId;
    }
}
