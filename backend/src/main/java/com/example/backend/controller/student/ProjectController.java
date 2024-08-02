package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.config.property.SystemConfig;
import com.example.backend.model.entity.message.ProjectSparkMessage;
import com.example.backend.model.entity.result.ProjectSparkCommonResult;
import com.example.backend.service.WebSocketService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RequestMapping("/api/student/project")
@RestController("StudentProjectController")
public class ProjectController extends BaseApiController {
    private final WebSocketService webSocketService;
    private final SystemConfig systemConfig;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Gson gson = new Gson();

    @Autowired
    public ProjectController(WebSocketService webSocketService, SystemConfig systemConfig) {
        this.webSocketService = webSocketService;
        this.systemConfig = systemConfig;
    }

    @PostMapping("/thought/chain/generate")
    public RestResponse<ProjectSparkCommonResult> thoughtChainGenerate(@RequestBody @Valid ProjectSparkMessage message){
        String receivedMessage = null;
        ProjectSparkCommonResult result = null;
        try {
            webSocketService.connect(systemConfig.getWebSocketPropertyConfig().getChainUrl());
            webSocketService.sendMessage(gson.toJson(message));

            receivedMessage = webSocketService.getReceivedMessage();
            result = objectMapper.readValue(receivedMessage,ProjectSparkCommonResult.class);
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
        return RestResponse.ok(result);
    }

}
