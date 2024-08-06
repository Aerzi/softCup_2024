package com.example.backend.model.request.student.spark.rewrite;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AIRewriteRequest {
    private Integer level;

    private String text;
}
