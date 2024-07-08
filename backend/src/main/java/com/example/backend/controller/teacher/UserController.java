package com.example.backend.controller.teacher;

import com.example.backend.base.BaseApiController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("TeacherUserController")
@RequestMapping(value = "/api/teacher/user")
public class UserController extends BaseApiController {

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('ROLE_TEACHER')")
    public String test(){
        System.out.println("---------->test/teacher");
        return "test/teacher .... ";
    }
}
