package com.github.pregrafer.Controller;

import com.github.pregrafer.Entity.RestBean;
import com.github.pregrafer.Service.AuthService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class RegisterPostController {
    @Resource
    AuthService authService;

    @PostMapping("/register-post")
    public RestBean<String> registerPost(@RequestParam("username") String username,
                                         @RequestParam("password") String password,
                                         @RequestParam("phone") String phone,
                                         @RequestParam("personid") String personid,
                                         @RequestParam("role") String role) {
        if (authService.registerPost(username, password, phone, personid, role))
            return RestBean.success("注册申请发送成功!");
        else
            return RestBean.failure(402, "用户名/电话/身份证号已被注册!");
    }
}
