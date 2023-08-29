package com.github.pregrafer.Controller;

import com.github.pregrafer.Entity.SimpleUserInfo;
import com.github.pregrafer.Mapper.UserMapper;
import com.github.pregrafer.Entity.RestBean;
import com.github.pregrafer.Entity.UserAccount;
import com.github.pregrafer.Service.AccountService;
import jakarta.annotation.Resource;
import lombok.Data;
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

    @PostMapping("/delete-account")
    public RestBean<String> deleteUser(@RequestParam("userid") int userid) {
        if (accountService.deleteUser(userid))
            return RestBean.success("删除成功!");
        else
            return RestBean.failure(410, "删除失败!");
    }

    @GetMapping("/get-info")
    public UserAccount getUserInfo(@RequestParam("username") String username) {
        return userMapper.findAccountByNameOrPhone(username);
    }

    @PostMapping("/update-info")
    public RestBean<String> updateUserInfo(@RequestBody UserAccount userAccount) {
        if (accountService.updateUserInfo(userAccount))
            return RestBean.success("信息保存成功!");
        else
            return RestBean.failure(407, "信息保存失败!");
    }

    @GetMapping("/me")
    public RestBean<SimpleUserInfo> getUserInfo(@SessionAttribute(name = "userInfo") SimpleUserInfo simpleUserInfo) {
        return RestBean.success(simpleUserInfo);
    }
}
