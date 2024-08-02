package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.ProgrammingAssess;
import com.example.backend.model.request.student.programmingassess.ProgrammingAssessEditRequest;
import com.example.backend.model.request.student.programmingassess.ProgrammingAssessSelectOneRequest;
import com.example.backend.service.ProgrammingAssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-18 11:25:45
 */
@RequestMapping("/api/student/programming/assess")
@RestController("StudentProgrammingAssessController")
public class ProgrammingAssessController extends BaseApiController {
    private final ProgrammingAssessService programmingAssessService;

    @Autowired
    public ProgrammingAssessController(ProgrammingAssessService programmingAssessService) {
        this.programmingAssessService = programmingAssessService;
    }

    @PostMapping("/select")
    public RestResponse<ProgrammingAssess> select(@RequestBody @Valid ProgrammingAssessSelectOneRequest request){
        return RestResponse.ok(programmingAssessService.select(request));
    }

    @PutMapping("/edit")
    public RestResponse edit(@RequestBody @Valid ProgrammingAssessEditRequest request){
        ProgrammingAssess programmingAssess = modelMapper.map(request,ProgrammingAssess.class);
        programmingAssessService.updateByIdFilter(programmingAssess);
        return RestResponse.ok();
    }

}
