package com.example.backend.model.entity.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectSparkNodeResult {
    private String name;

    private List<ProjectSparkNodeResult> children;
}
