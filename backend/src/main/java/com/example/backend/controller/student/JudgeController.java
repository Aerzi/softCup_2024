package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/student/judge")
@RestController("StudentJudgeController")
public class JudgeController extends BaseApiController {
    private final JudgeService judgeService;

    @Autowired
    public JudgeController(JudgeService judgeService) {
        this.judgeService = judgeService;
    }

    @PostMapping("/submit")
    public RestResponse<String> submit(@RequestBody String json){
        String token = judgeService.createSubmission(json);
        return judgeService.getJudgeResult(token);
    }

    @GetMapping("/status")
    public RestResponse<String> status(){
        return judgeService.getJudgeStatus();
    }

    @GetMapping("/lang")
    public RestResponse<String> lang(){
        return judgeService.getJudgeLanguage();
    }
}
