package com.example.backend.model.entity.aippt;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AIPPTChapter {
    private String id;
    private String chapterTitle;
    private String fileUrl;
    private Integer fileType;
    private Boolean chartFlag;
    private Boolean searchFlag;
    private List<AIPPTChapter> chapterContents;
}
