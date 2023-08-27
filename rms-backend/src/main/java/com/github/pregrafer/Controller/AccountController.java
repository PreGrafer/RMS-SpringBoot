package com.github.pregrafer.Controller;

import com.github.pregrafer.Mapper.UserMapper;
import com.github.pregrafer.Pojo.RestBean;
import com.github.pregrafer.Pojo.UserAccount;
import com.github.pregrafer.Service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Resource
    UserMapper userMapper;
    @Resource
    AccountService accountService;

    @GetMapping("/account-list")
    public List<UserAccount> getAccountList() {
        return userMapper.getAccounts();
    }

    @PostMapping("delete-account")
    public RestBean<String> deleteUser(@RequestParam("userid") int userid) {
        if (accountService.deleteUser(userid))
            return RestBean.success("删除成功!");
        else
            return RestBean.failure(410, "删除失败!");
    }
}
