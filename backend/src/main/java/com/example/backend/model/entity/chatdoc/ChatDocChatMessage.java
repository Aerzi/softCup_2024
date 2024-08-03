package com.example.backend.model.entity.chatdoc;

import lombok.Getter;
import lombok.Setter;

/**
 * ChatMessage
 *
 * @author feixia0g
 * @version 2024/08/3 16:20
 **/
@Getter
@Setter
public class ChatDocChatMessage {

    private String role;

    private String content;
}
