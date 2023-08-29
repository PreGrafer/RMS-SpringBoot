package com.github.pregrafer.Controller;

import com.github.pregrafer.Mapper.RegisterMapper;
import com.github.pregrafer.Entity.RegisterAccount;
import com.github.pregrafer.Entity.RestBean;
import com.github.pregrafer.Service.RegisterService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/register")
public class RegisterController {
    @Resource
    RegisterMapper registerMapper;
    @Resource
    RegisterService registerService;

    @GetMapping("/register-list")
    public List<RegisterAccount> getRegisterList() {
        return registerMapper.getRegisterAccounts();
    }

    @GetMapping("/worker-register-list")
    public List<RegisterAccount> getWorkerRegisterList() {
        return registerMapper.getRegisterAccountsByRole("worker");
    }

    @GetMapping("/user-register-list")
    public List<RegisterAccount> getUserRegisterList() {
        return registerMapper.getRegisterAccountsByRole("user");
    }

    @PostMapping("/pass-register")
    public RestBean<String> passRegister(@RequestBody RegisterAccount registerAccount) {
        if (registerService.passRegister(registerAccount))
            return RestBean.success("已通过注册申请!");
        else
            return RestBean.failure(405, "出现了一些错误,请联系管理员!");
    }

    @PostMapping("/reject-register")
    public RestBean<String> rejectRegister(@RequestParam("username") String username) {
        if (registerService.rejectRegister(username))
            return RestBean.success("已拒绝注册申请!");
        else
            return RestBean.failure(406, "出现了一些错误,请联系管理员!");
    }

}
