package com.example.backend.model.request.student.pdf;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PDFDownloadRequest {
    private List<String> result;

    private String fileName;
}
