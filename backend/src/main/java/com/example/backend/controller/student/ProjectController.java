package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.Project;
import com.example.backend.model.request.student.project.ProjectAddRequest;
import com.example.backend.model.request.student.project.ProjectEditRequest;
import com.example.backend.model.request.student.project.ProjectPageRequest;
import com.example.backend.model.request.student.project.ProjectResponse;
import com.example.backend.service.ProjectService;
import com.example.backend.utils.PageInfoHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/student/project")
@RestController("StudentProjectController")
public class ProjectController extends BaseApiController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
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

}
