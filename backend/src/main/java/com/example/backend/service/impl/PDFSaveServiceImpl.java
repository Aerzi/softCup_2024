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

import java.io.*;

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
//        String fontPath = "src/main/resources/fonts/simsun.ttc,1"; // 替换为系统中存在的字体路径

        // 加载支持中文的字体
        InputStream fontStream = getClass().getClassLoader().getResourceAsStream("fonts/simsun.ttc");
        if (fontStream == null) {
            throw new RuntimeException("Font not found in classpath");
        }

        // 创建临时文件存储字体
        File tempFontFile = null;
        try {
            tempFontFile = File.createTempFile("simsun", ".ttc");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (FileOutputStream tempOutputStream = new FileOutputStream(tempFontFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fontStream.read(buffer)) != -1) {
                tempOutputStream.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 使用临时文件的路径来创建字体
        String fontPath = tempFontFile.getAbsolutePath() + ",1";

        System.out.println(fontPath);

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
