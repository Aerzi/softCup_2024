package com.example.backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backend.model.request.student.pdf.PDFDownloadRequest;
import com.example.backend.service.FileUploadService;
import com.example.backend.service.PDFSaveService;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PDFSaveServiceImpl implements PDFSaveService {
    private final FileUploadService fileUploadService;

    @Autowired
    public PDFSaveServiceImpl(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }


    @Override
    public String pdfGenerate(PDFDownloadRequest request) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // 创建PDF写入器，并指定输出路径
        Document document = null;
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        document = new Document(pdfDoc);

        // 加载支持中文的字体
        String fontPath = "src/main/resources/fonts/simsun.ttc,1"; // 替换为系统中存在的字体路径
        PdfFont font = null;
        try {
            font = PdfFontFactory.createFont(fontPath, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        document.setFont(font);

        // 遍历result列表，添加到PDF
        for (String resultItem : request.getResult()) {
            // 替换换行符为PDF中的段落
            String[] lines = resultItem.split("\\n\\n");
            for (String line : lines) {
                document.add(new Paragraph(line).setFont(font));
            }
            document.add(new Paragraph("\n"));
        }

        // 关闭文档
        document.close();
        System.out.println("PDF文件已生成！");

        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        String filePath = fileUploadService.uploadFile(inputStream,0,null);

        return filePath;
    }
}
