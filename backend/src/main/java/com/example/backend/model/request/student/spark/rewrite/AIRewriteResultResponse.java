package com.example.backend.model.request.student.spark.rewrite;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AIRewriteResultResponse {
    private String text;

    private List<int[]> coordinates;
}
