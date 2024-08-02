package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.EventLogMessage;
import com.example.backend.base.RestResponse;
import com.example.backend.config.property.SystemConfig;
import com.example.backend.event.UserEvent;
import com.example.backend.model.entity.UserEventLog;
import com.example.backend.model.entity.message.ProjectSparkMessage;
import com.example.backend.model.entity.result.ProjectSparkCommonResult;
import com.example.backend.service.WebSocketService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@RequestMapping("/api/student/project")
@RestController("StudentProjectController")
public class ProjectController extends BaseApiController {
    private final WebSocketService webSocketService;
    private final ApplicationEventPublisher eventPublisher;
    private final SystemConfig systemConfig;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Gson gson = new Gson();

    @Autowired
    public ProjectController(WebSocketService webSocketService, ApplicationEventPublisher eventPublisher, SystemConfig systemConfig) {
        this.webSocketService = webSocketService;
        this.eventPublisher = eventPublisher;
        this.systemConfig = systemConfig;
    }

    @PostMapping("/thought/chain/generate")
    public RestResponse<ProjectSparkCommonResult> thoughtChainGenerate(@RequestBody @Valid ProjectSparkMessage message) {
        String receivedMessage = null;
        ProjectSparkCommonResult result = null;
        try {
            webSocketService.connect(systemConfig.getWebSocketPropertyConfig().getChainUrl());
            webSocketService.sendMessage(gson.toJson(message));

            receivedMessage = webSocketService.getReceivedMessage();
            result = objectMapper.readValue(receivedMessage, ProjectSparkCommonResult.class);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UserEventLog userEventLog = new UserEventLog();
        userEventLog.setUserId(getCurrentUser().getId());
        userEventLog.setUserName(getCurrentUser().getUserName());
        userEventLog.setCreateTime(new Date());
        userEventLog.setContent(getCurrentUser().getUserName() + EventLogMessage.THOUGHT_CHAIN + EventLogMessage.HELP + EventLogMessage.PROJECT);
        eventPublisher.publishEvent(new UserEvent(userEventLog));

        return RestResponse.ok(result);
    }

}
