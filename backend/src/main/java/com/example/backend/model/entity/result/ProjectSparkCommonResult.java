package com.example.backend.model.entity.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectSparkCommonResult {
    private List<String> result;
    private ProjectSparkNodeResult tree_structure;
}
