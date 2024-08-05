package com.example.backend.controller.teacher;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.aitranslation.AITranslationDocResponse;
import com.example.backend.model.entity.aitranslation.AITranslationRequest;
import com.example.backend.model.entity.aitranslation.AITranslationText;
import com.example.backend.service.FileUploadService;
import com.example.backend.service.PDFTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@RequestMapping("/api/teacher/spark/translation")
@RestController("TeacherSparkTranslationController")
public class PDFTranslationController extends BaseApiController {
    private final PDFTranslationService pdfTranslationService;
    private final FileUploadService fileUploadService;

    @Autowired
    public PDFTranslationController(PDFTranslationService pdfTranslationService, FileUploadService fileUploadService) {
        this.pdfTranslationService = pdfTranslationService;
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/translate")
    public RestResponse<AITranslationText> translate(@RequestBody @Valid AITranslationRequest request){
        AITranslationText text = pdfTranslationService.translate(request.getText());
        return RestResponse.ok(text);
    }

    @PostMapping("/doc/translate")
    public RestResponse<AITranslationDocResponse> translateByDoc(@RequestParam("file") MultipartFile multipartFile){
        long attachSize = multipartFile.getSize();
        String fileName = multipartFile.getOriginalFilename();
        String url = null;
        try (InputStream inputStream = multipartFile.getInputStream()) {
            String filePath = fileUploadService.uploadFile(inputStream, attachSize, fileName);
            url = filePath;
            System.out.println(url);
            System.out.println(fileName);
        } catch (IOException e) {
            return RestResponse.fail(2, e.getMessage());
        }

        // 定义字符集和每次读取的最大字节数
        Charset charset = StandardCharsets.UTF_8;
        int maxBytes = 5000; // 中文字符限制

        AITranslationDocResponse text = pdfTranslationService.translateDoc(multipartFile, maxBytes, charset);
        return RestResponse.ok(text);
    }
}