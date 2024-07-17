package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.ProjectStep;
import com.example.backend.model.request.student.projectstep.ProjectStepPageRequest;
import com.example.backend.model.request.student.projectstep.ProjectStepResponse;
import com.example.backend.model.request.student.projectstep.ProjectStepsAddRequest;
import com.example.backend.model.request.student.projectstep.ThoughtChainRequest;
import com.example.backend.service.ProjectStepService;
import com.example.backend.utils.PageInfoHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-17 03:22:14
 */
@RequestMapping("/api/student/project/step")
@RestController("StudentProjectStepController")
public class ProjectStepController extends BaseApiController {
    private final ProjectStepService projectStepService;

    @Autowired
    public ProjectStepController(ProjectStepService projectStepService) {
        this.projectStepService = projectStepService;
    }

    //当前项目的思维链步骤保存
    @PostMapping("/save")
    public RestResponse add(@RequestBody @Valid ProjectStepsAddRequest request) {
        List<ThoughtChainRequest> requests = request.getRequests();
        List<ProjectStep> projectSteps = requests.stream().map(e -> {
                    ProjectStep projectStep = modelMapper.map(e, ProjectStep.class);
                    projectStep.setDeleted(false);
                    projectStep.setProjId(request.getProjId());
                    return projectStep;
                }
        ).collect(Collectors.toList());
        for (ProjectStep projectStep : projectSteps) {
            projectStepService.insertByFilter(projectStep);
        }
        return RestResponse.ok();
    }

    @PostMapping("/page")
    public RestResponse page(@RequestBody @Valid ProjectStepPageRequest request) {
        PageInfo<ProjectStep> pageInfo = projectStepService.page(request);
        PageInfo<ProjectStepResponse> page = PageInfoHelper.copyMap(pageInfo, e -> modelMapper.map(e, ProjectStepResponse.class));
        return RestResponse.ok(page);
    }

    @DeleteMapping("/delete/{id}")
    public RestResponse delete(@PathVariable Integer id){
        projectStepService.deleteByIdFilter(id);
        return RestResponse.ok();
    }
}
