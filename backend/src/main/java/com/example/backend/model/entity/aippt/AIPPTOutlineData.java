package com.example.backend.model.entity.aippt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AIPPTOutlineData {
    private String sid;
    private String coverImgSrc;
    private String title;
    private String subTitle;
    private AIPPTOutline outline;
}
