package com.example.backend.model.entity.airewrite;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AIRewriteResponse {
    private AIRewriteHeader header;

    private AIRewritePayload payload;
}
