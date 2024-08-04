package com.example.backend.service;

import com.example.backend.model.request.student.pdf.PDFDownloadRequest;
import com.itextpdf.layout.Document;

public interface PDFSaveService {
    public String pdfGenerate(PDFDownloadRequest request);
}
