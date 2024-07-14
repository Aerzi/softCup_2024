package com.example.backend.controller.teacher;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.ClassStudent;
import com.example.backend.model.request.teacher.calssstudent.ClassStudentAddRequest;
import com.example.backend.model.request.teacher.calssstudent.ClassStudentBatchAddRequest;
import com.example.backend.model.request.teacher.calssstudent.ClassStudentPageRequest;
import com.example.backend.model.request.teacher.calssstudent.ClassStudentResponse;
import com.example.backend.service.ClassStudentService;
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
 * @since 2024-07-14 17:07:34
 */
@RequestMapping("/api/student/class/student")
@RestController("TeacherClassStudentController")
public class ClassStudentController extends BaseApiController {
    private final ClassStudentService classStudentService;

    @Autowired
    public ClassStudentController(ClassStudentService classStudentService) {
        this.classStudentService = classStudentService;
    }

    @PostMapping("/add")
    public RestResponse addStudent(@RequestBody @Valid ClassStudentAddRequest request) {
        ClassStudent classStudent = modelMapper.map(request, ClassStudent.class);
        classStudentService.insertByFilter(classStudent);
        return RestResponse.ok();
    }

    @PostMapping("/batch/add")
    public RestResponse batchAddStudent(@RequestBody @Valid ClassStudentBatchAddRequest batchAddRequest) {
        List<ClassStudentAddRequest> requests = batchAddRequest.getRequests();
        List<ClassStudent> classStudentRequest = requests.stream()
                .map(request -> modelMapper.map(request, ClassStudent.class)
                ).collect(Collectors.toList());
        classStudentService.batchInsertByFilter(classStudentRequest);
        return RestResponse.ok();
    }

    //查询某个课堂的所有学生情况
    @GetMapping("/page")
    public RestResponse<ClassStudentResponse> page(@RequestBody @Valid ClassStudentPageRequest request) {
        ClassStudentResponse response = new ClassStudentResponse();
        response.setStudents(classStudentService.page(request));
        response.setClassId(request.getClassId());
        return RestResponse.ok(response);
    }

    //将学生踢出课堂
    @DeleteMapping("/delete/{userId}")
    public RestResponse deleteByUserId(@PathVariable Integer userId) {
        classStudentService.deleteByUserId(userId);
        return RestResponse.ok();
    }

    //批量将学生踢出课堂
    @DeleteMapping("/batch/delete")
    public RestResponse deleteByUserIds(@RequestParam List<Integer> userIds) {
        for (Integer userId : userIds) classStudentService.deleteByUserId(userId);
        return RestResponse.ok();
    }

}
