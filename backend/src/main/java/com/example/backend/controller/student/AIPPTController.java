package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.aippt.AIPPTTemplate;
import com.example.backend.service.AIPPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/student/spark/ppt")
@RestController("StudentSparkPPTController")
public class AIPPTController extends BaseApiController {
    private final AIPPTService aipptService;

    @Autowired
    public AIPPTController(AIPPTService aipptService) {
        this.aipptService = aipptService;
    }

    @GetMapping("/themeList")
    public RestResponse<AIPPTTemplate> themeList(){
        AIPPTTemplate aipptTemplate = aipptService.getTemplateList();

        return RestResponse.ok(aipptTemplate);
    }




}
