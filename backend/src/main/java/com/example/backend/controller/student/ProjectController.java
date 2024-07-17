package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.config.property.SystemConfig;
import com.example.backend.model.entity.Project;
import com.example.backend.model.entity.message.ProjectSparkMessage;
import com.example.backend.model.entity.result.ProjectSparkResult;
import com.example.backend.model.request.student.project.ProjectAddRequest;
import com.example.backend.model.request.student.project.ProjectEditRequest;
import com.example.backend.model.request.student.project.ProjectPageRequest;
import com.example.backend.model.request.student.project.ProjectResponse;
import com.example.backend.service.ProjectService;
import com.example.backend.service.WebSocketService;
import com.example.backend.utils.PageInfoHelper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RequestMapping("/api/student/project")
@RestController("StudentProjectController")
public class ProjectController extends BaseApiController {
    private final ProjectService projectService;
    private final WebSocketService webSocketService;
    private final SystemConfig systemConfig;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Gson gson = new Gson();

    @Autowired
    public ProjectController(ProjectService projectService, WebSocketService webSocketService, SystemConfig systemConfig) {
        this.projectService = projectService;
        this.webSocketService = webSocketService;
        this.systemConfig = systemConfig;
    }

    @PostMapping("/add")
    public RestResponse add(@RequestBody @Valid ProjectAddRequest request) {
        Project project = modelMapper.map(request, Project.class);
        projectService.insertByFilter(project);
        return RestResponse.ok();
    }

    @GetMapping("/select/{id}")
    public RestResponse<ProjectResponse> select(Integer id) {
        Project project = projectService.select(id);
        ProjectResponse response = modelMapper.map(project, ProjectResponse.class);
        response.setFinishedCondition(project.getFinishedCondition() / 10.0);
        return RestResponse.ok(response);
    }

    @PostMapping("/page")
    public RestResponse<PageInfo<ProjectResponse>> page(@RequestBody @Valid ProjectPageRequest request) {
        PageInfo<Project> pageInfo = projectService.page(request);
        PageInfo<ProjectResponse> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ProjectResponse response = modelMapper.map(e, ProjectResponse.class);
            response.setFinishedCondition(e.getFinishedCondition() / 10.0);
            return response;
        });
        return RestResponse.ok(page);
    }

    @PutMapping("/edit")
    public RestResponse edit(@RequestBody @Valid ProjectEditRequest request){
        Project project = modelMapper.map(request,Project.class);
        projectService.updateByIdFilter(project);
        return RestResponse.ok();
    }

    @DeleteMapping("/delete/{id}")
    public RestResponse delete(@PathVariable Integer id){
        projectService.deleteByIdFilter(id);
        return RestResponse.ok();
    }

    @PostMapping("/thought/chain/generate")
    public RestResponse<ProjectSparkResult> thoughtChainGenerate(@RequestBody @Valid ProjectSparkMessage message){
        String receivedMessage = null;
        ProjectSparkResult result = null;
        try {
            webSocketService.connect(systemConfig.getWebSocketPropertyConfig().getUrl());
            webSocketService.sendMessage(gson.toJson(message));

            receivedMessage = webSocketService.getReceivedMessage();
            result = objectMapper.readValue(receivedMessage, ProjectSparkResult.class);
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
