package com.example.backend.controller.student;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.Class;
import com.example.backend.model.entity.ClassStudent;
import com.example.backend.model.request.student.aclass.*;
import com.example.backend.service.ClassService;
import com.example.backend.service.ClassStudentService;
import com.example.backend.utils.DateTimeUtil;
import com.example.backend.utils.PageInfoHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    private final ClassService classService;

    @Autowired
    public ClassController(ClassStudentService classStudentService, ClassService classService) {
        this.classStudentService = classStudentService;
        this.classService = classService;
    }

    //学生加入课程
    @PostMapping("/join")
    public RestResponse joinClass(@RequestBody @Valid ClassStudentJoinRequest request){
        ClassStudent classStudent = modelMapper.map(request,ClassStudent.class);
        classStudent.setUserId(getCurrentUser().getId());
        classStudentService.insertByFilter(classStudent);
        return RestResponse.ok();
    }

    //查询学生在哪个教室
    @GetMapping("/select")
    public RestResponse<ClassStudentResponse> atClass(){
        ClassStudentResponse response = classStudentService.atClass(getCurrentUser().getId());
        return RestResponse.ok(response);
    }

    @PostMapping("/page")
    public RestResponse<PageInfo<ClassResponse>> page(@RequestBody ClassPageRequest request){
        PageInfo<Class> pageInfo = classService.page(request);
        PageInfo<ClassResponse> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ClassResponse response =  modelMapper.map(e, ClassResponse.class);
            response.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            response.setModifyTime(DateTimeUtil.dateFormat(e.getModifyTime()));
            return response;
        });
        return RestResponse.ok(page);
    }

    //查询
    @GetMapping("/list")
    public RestResponse<List<Class>> list(){
        List<Class> classes = classService.list();
        return RestResponse.ok(classes);
    }

    //学生退出课程
    @DeleteMapping("/exit")
    public RestResponse exitClass(@RequestBody ClassStudentExitRequest request){
        ClassStudent classStudent  = new ClassStudent();
        classStudent.setUserId(getCurrentUser().getId());
        classStudent.setClassId(request.getClassId());
        classStudentService.deleteByClassIdAndUserId(classStudent);
        return RestResponse.ok();
    }
}
