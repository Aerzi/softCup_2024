package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.ProgrammingAssess;
import com.example.backend.model.request.student.programmingassess.ProgrammingAssessEditRequest;
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

    @GetMapping("/select/{id}")
    public RestResponse<ProgrammingAssess> select(@PathVariable Integer id){
        return RestResponse.ok(programmingAssessService.select(id));
    }

    @PutMapping("/edit")
    public RestResponse edit(@RequestBody @Valid ProgrammingAssessEditRequest request){
        ProgrammingAssess programmingAssess = modelMapper.map(request,ProgrammingAssess.class);
        programmingAssessService.updateByIdFilter(programmingAssess);
        return RestResponse.ok();
    }

}
