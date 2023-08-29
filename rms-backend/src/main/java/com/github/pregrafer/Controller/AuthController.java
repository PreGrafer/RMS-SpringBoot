package com.github.pregrafer.Controller;

import com.github.pregrafer.Entity.RegisterAccount;
import com.github.pregrafer.Entity.RestBean;
import com.github.pregrafer.Service.AuthService;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Resource
    AuthService authService;

    @PostMapping("/register-post")
    public RestBean<String> registerPost(@RequestBody RegisterAccount registerAccount) {
        if (authService.registerPost(registerAccount.getUsername(), registerAccount.getPassword(), registerAccount.getPhone(), registerAccount.getPersonid(), registerAccount.getRole()))
            return RestBean.success("注册申请发送成功!");
        else
            return RestBean.failure(402, "用户名/电话/身份证号已被注册!");
    }

    @PostMapping("/reset-password")
    public RestBean<String> resetPassword(@RequestBody ResetPasswordRequest requestData) {
        if (authService.resetPassword(requestData.getUsername(), requestData.getPassword(), requestData.getPhone(), requestData.getPersonid())) {
            return RestBean.success("密码重置成功!");
        } else {
            return RestBean.failure(403, "用户信息不匹配，无法重置密码");
        }
    }

    @GetMapping("/get-role/{username}")
    public RestBean<String> getUserRole(@PathVariable String username) {
        String role = authService.getUserRoleByUserName(username);
        if (role != null) {
            return RestBean.success(role);
        } else {
            return RestBean.failure(404, "获取角色失败");
        }
    }

    @Data
    public static class ResetPasswordRequest {
        String username, password, phone, personid;
    }
}
