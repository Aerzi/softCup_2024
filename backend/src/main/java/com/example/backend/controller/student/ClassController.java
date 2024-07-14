package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.ClassStudent;
import com.example.backend.model.request.student.aclass.ClassStudentExitRequest;
import com.example.backend.model.request.student.aclass.ClassStudentJoinRequest;
import com.example.backend.model.request.student.aclass.ClassStudentResponse;
import com.example.backend.service.ClassStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author feixia0g
 * @since 2024-07-14 10:50:20
 */
@RequestMapping("/api/student/class")
@RestController("StudentClassController")
public class ClassController extends BaseApiController {
    private final ClassStudentService classStudentService;

    @Autowired
    public ClassController(ClassStudentService classStudentService) {
        this.classStudentService = classStudentService;
    }

    //学生加入课程
    @PostMapping("/join")
    public RestResponse joinClass(@RequestParam @Valid ClassStudentJoinRequest request){
        ClassStudent classStudent = modelMapper.map(request,ClassStudent.class);
        classStudent.setUserId(getCurrentUser().getId());
        classStudentService.insertByFilter(classStudent);
        return RestResponse.ok();
    }

    //查询学生在哪个教室
    @GetMapping("/{userId}")
    public RestResponse<ClassStudentResponse> atClass(@PathVariable Integer userId){
        ClassStudentResponse response = classStudentService.atClass(userId);
        return RestResponse.ok(response);
    }

    //学生退出课程
    @DeleteMapping("/exit")
    public RestResponse exitClass(@PathVariable ClassStudentExitRequest request){
        classStudentService.deleteByClassIdAndUserId(request);
        return RestResponse.ok();
    }
}
