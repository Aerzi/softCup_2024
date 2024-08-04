package com.example.backend.model.entity.aippt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AIPPTByDocRequest {
    private String query;
    private String theme;
    private String business_id;
    private String author;
    private Boolean is_card_note;
    private Boolean is_cover_img;
    private String language;
    private Boolean is_figure;
}
