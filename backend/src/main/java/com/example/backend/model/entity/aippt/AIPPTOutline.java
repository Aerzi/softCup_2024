package com.example.backend.model.entity.aippt;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AIPPTOutline {
    private String id;
    private String title;
    private String subTitle;
    private String fileUrl;
    private Integer fileType;
    private List<AIPPTChapter> chapters;
}
