package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.request.student.spark.rewrite.AIRewriteRequest;
import com.example.backend.model.request.student.spark.rewrite.AIRewriteResultResponse;
import com.example.backend.service.AIRewriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/student/spark/rewrite")
@RestController("StudentSparkWriteController")
public class AIRewriteController extends BaseApiController {
    private final AIRewriteService aiRewriteService;

    @Autowired
    public AIRewriteController(AIRewriteService aiRewriteService) {
        this.aiRewriteService = aiRewriteService;
    }

    @PostMapping("/generate")
    public RestResponse<AIRewriteResultResponse> rewrite(@RequestBody @Valid AIRewriteRequest request){
        AIRewriteResultResponse rewriteResultResponse = aiRewriteService.rewrite(request);
        System.out.println(rewriteResultResponse);
        return RestResponse.ok(rewriteResultResponse);
    }

}
