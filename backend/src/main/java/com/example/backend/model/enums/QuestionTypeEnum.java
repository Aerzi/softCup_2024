package com.example.backend.model.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum QuestionTypeEnum {
    OBJECTIVE("objective"),
    PROGRAMMING("programming");

    private final String description;

    QuestionTypeEnum(String description) {
        this.description = description;
    }

}
