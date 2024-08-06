package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.EventLogMessage;
import com.example.backend.base.RestResponse;
import com.example.backend.event.UserEvent;
import com.example.backend.model.entity.UserEventLog;
import com.example.backend.model.request.student.spark.rewrite.AIRewriteRequest;
import com.example.backend.model.request.student.spark.rewrite.AIRewriteResultResponse;
import com.example.backend.service.AIRewriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RequestMapping("/api/student/spark/rewrite")
@RestController("StudentSparkWriteController")
public class AIRewriteController extends BaseApiController {
    private final AIRewriteService aiRewriteService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public AIRewriteController(AIRewriteService aiRewriteService, ApplicationEventPublisher eventPublisher) {
        this.aiRewriteService = aiRewriteService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/generate")
    public RestResponse<AIRewriteResultResponse> rewrite(@RequestBody @Valid AIRewriteRequest request){
        AIRewriteResultResponse rewriteResultResponse = aiRewriteService.rewrite(request);
        System.out.println(rewriteResultResponse);

        //文本改写 日志记录
        UserEventLog userEventLog = new UserEventLog();
        userEventLog.setDeleted(false);
        userEventLog.setCreateTime(new Date());
        userEventLog.setUserId(getCurrentUser().getId());
        userEventLog.setUserName(getCurrentUser().getUserName());
        userEventLog.setCreateTime(new Date());
        userEventLog.setContent(getCurrentUser().getUserName() + EventLogMessage.REWRITE);
        eventPublisher.publishEvent(new UserEvent(userEventLog));

        return RestResponse.ok(rewriteResultResponse);
    }

}
