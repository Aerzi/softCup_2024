package com.example.backend.model.request.student.spark.chatdoc;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter

public class ChatDocChatRequest {
    @NotBlank
    private String fileId;

    @NotBlank
    private String question;
}
