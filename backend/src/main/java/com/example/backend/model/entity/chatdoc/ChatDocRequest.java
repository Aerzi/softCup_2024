package com.example.backend.model.entity.chatdoc;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ChatDocRequest {
    private List<String> fileIds;

    private List<ChatDocChatMessage> messages;

    private Integer topN;
}
