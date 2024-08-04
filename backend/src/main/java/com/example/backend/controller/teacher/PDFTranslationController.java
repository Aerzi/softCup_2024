package com.example.backend.controller.teacher;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.aitranslation.AITranslationRequest;
import com.example.backend.model.entity.aitranslation.AITranslationText;
import com.example.backend.service.PDFTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/teacher/spark/translation")
@RestController("TeacherSparkTranslationController")
public class PDFTranslationController extends BaseApiController {
    private final PDFTranslationService pdfTranslationService;

    @Autowired
    public PDFTranslationController(PDFTranslationService pdfTranslationService) {
        this.pdfTranslationService = pdfTranslationService;
    }

    @PostMapping("/translate")
    public RestResponse<AITranslationText> translate(@RequestBody @Valid AITranslationRequest request){
        AITranslationText text = pdfTranslationService.translate(request.getText());
        return RestResponse.ok(text);
    }

}
