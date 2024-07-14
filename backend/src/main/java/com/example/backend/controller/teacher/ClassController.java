package com.example.backend.controller.teacher;

import com.example.backend.base.BaseApiController;
import com.example.backend.base.RestResponse;
import com.example.backend.model.entity.Class;
import com.example.backend.model.request.teacher.aclass.ClassEditRequest;
import com.example.backend.model.request.teacher.aclass.ClassPageRequest;
import com.example.backend.model.request.teacher.aclass.ClassCreateRequest;
import com.example.backend.model.request.teacher.aclass.ClassResponse;
import com.example.backend.service.ClassService;
import com.example.backend.service.ClassStudentService;
import com.example.backend.utils.DateTimeUtil;
import com.example.backend.utils.PageInfoHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RequestMapping("/api/teacher/class")
@RestController("TeacherClassController")
public class ClassController extends BaseApiController {
    private final ClassService classService;
    private final ClassStudentService classStudentService;

    @Autowired
    public ClassController(ClassService classService, ClassStudentService classStudentService) {
        this.classService = classService;
        this.classStudentService = classStudentService;
    }

    @PostMapping("/create")
    public RestResponse createClass(@RequestBody @Valid ClassCreateRequest request) {
        com.example.backend.model.entity.Class aClass = modelMapper.map(request, com.example.backend.model.entity.Class.class);
        aClass.setCreateTime(new Date());
        aClass.setDeleted(false);
        aClass.setStatus(1);
        aClass.setUserId(getCurrentUser().getId());
        classService.insertByFilter(aClass);
        return RestResponse.ok();
    }

    @GetMapping("/select/{id}")
    public RestResponse<ClassResponse> select(@PathVariable Integer id){
        Class aclass = classService.select(id);
        ClassResponse response = modelMapper.map(aclass,ClassResponse.class);
        response.setCreateTime(DateTimeUtil.dateFormat(aclass.getCreateTime()));
        response.setModifyTime(DateTimeUtil.dateFormat(aclass.getModifyTime()));
        return RestResponse.ok(response);
    }

    @GetMapping("/list")
    public RestResponse<List<Class>> list() {
        List<Class> classes = classService.list();
        return RestResponse.ok(classes);
    }

    @PostMapping("/page")
    public RestResponse<PageInfo<ClassResponse>> page(@RequestBody ClassPageRequest request) {
        PageInfo<Class> pageInfo = classService.page(request);
        PageInfo<ClassResponse> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ClassResponse response =  modelMapper.map(e, ClassResponse.class);
            response.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            response.setModifyTime(DateTimeUtil.dateFormat(e.getModifyTime()));
            return response;
        });
        return RestResponse.ok(page);
    }

    @PutMapping("/edit")
    public RestResponse edit(@RequestBody @Valid ClassEditRequest request){
        Class aclass = modelMapper.map(request,Class.class);
        aclass.setModifyTime(new Date());
        aclass.setUserId(getCurrentUser().getId());
        classService.updateByIdFilter(aclass);
        return RestResponse.ok();
    }

    //解散课堂
    @DeleteMapping("/delete/{id}")
    public RestResponse delete(@PathVariable Integer id){
        classService.deleteByIdFilter(id);
        classStudentService.deleteByClassId(id);
        return RestResponse.ok();
    }

}
