package com.example.backend.controller.teacher;

import com.alibaba.fastjson.JSON;
import com.example.backend.base.BaseApiController;
import com.example.backend.base.EventLogMessage;
import com.example.backend.base.RestResponse;
import com.example.backend.event.UserEvent;
import com.example.backend.model.entity.UserEventLog;
import com.example.backend.model.entity.aippt.*;
import com.example.backend.service.AIPPTService;
import com.example.backend.service.FileUploadService;
import com.example.backend.websocket.AIPPTProgressWebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@RequestMapping("/api/teacher/spark/ppt")
@RestController("TeacherSparkPPTController")
public class AIPPTController extends BaseApiController {
    private final AIPPTService aipptService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public AIPPTController(AIPPTService aipptService, FileUploadService fileUploadService, ApplicationEventPublisher eventPublisher) {
        this.aipptService = aipptService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/themeList")
    public RestResponse<AIPPTTemplate> themeList() {
        AIPPTTemplate aipptTemplate = aipptService.getTemplateList();

        //ppt模板 日志记录
        UserEventLog userEventLog = new UserEventLog();
        userEventLog.setDeleted(false);
        userEventLog.setCreateTime(new Date());
        userEventLog.setUserId(getCurrentUser().getId());
        userEventLog.setUserName(getCurrentUser().getUserName());
        userEventLog.setCreateTime(new Date());
        userEventLog.setContent(getCurrentUser().getUserName() + EventLogMessage.PPT_THEME);
        eventPublisher.publishEvent(new UserEvent(userEventLog));

        return RestResponse.ok(aipptTemplate);
    }

    @PostMapping("/outline")
    public RestResponse<AIPPTOutlineResponse> outline(@RequestBody @Valid AIPPTOutlineRequest request) {
        AIPPTOutlineResponse aipptOutlineResponse = aipptService.outline(request);

        //大纲生成 日志记录
        UserEventLog userEventLog = new UserEventLog();
        userEventLog.setDeleted(false);
        userEventLog.setCreateTime(new Date());
        userEventLog.setUserId(getCurrentUser().getId());
        userEventLog.setUserName(getCurrentUser().getUserName());
        userEventLog.setCreateTime(new Date());
        userEventLog.setContent(getCurrentUser().getUserName() + EventLogMessage.PPT_OUTLINE);
        eventPublisher.publishEvent(new UserEvent(userEventLog));

        return RestResponse.ok(aipptOutlineResponse);
    }

    @PostMapping("/ppt")
    public RestResponse<AIPPTResponse> ppt(@RequestBody @Valid AIPPTRequest request) {
        AIPPTResponse aipptResponse = aipptService.ppt(request);

        //根据文档生成大纲 日志记录
        UserEventLog userEventLog = new UserEventLog();
        userEventLog.setDeleted(false);
        userEventLog.setCreateTime(new Date());
        userEventLog.setUserId(getCurrentUser().getId());
        userEventLog.setUserName(getCurrentUser().getUserName());
        userEventLog.setCreateTime(new Date());
        userEventLog.setContent(getCurrentUser().getUserName() + EventLogMessage.PPT_QUERY);
        eventPublisher.publishEvent(new UserEvent(userEventLog));

        return RestResponse.ok(aipptResponse);
    }

    @GetMapping("/ppt/progress/{sid}")
    public RestResponse checkProgress(@PathVariable String sid) {
        int progress = 0;
        AIPPTProgressResult result;
        while (progress < 100) {
            result = aipptService.checkProgress(sid);
            progress = result.getData().getProcess();

            String jsonString = JSON.toJSONString(result);
            try {
                AIPPTProgressWebSocketServer.sendInfo(jsonString);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (progress < 100) {
                try {
                    Thread.sleep(5000); // 暂停2秒
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //查询ppt生成进度 日志记录
        UserEventLog userEventLog = new UserEventLog();
        userEventLog.setUserId(getCurrentUser().getId());
        userEventLog.setUserName(getCurrentUser().getUserName());
        userEventLog.setCreateTime(new Date());
        userEventLog.setContent(getCurrentUser().getUserName() + EventLogMessage.PPT_PROGRESS);
        eventPublisher.publishEvent(new UserEvent(userEventLog));

        return RestResponse.ok();
    }
}
