package com.github.pregrafer.Controller;

import com.github.pregrafer.Mapper.UserMapper;
import com.github.pregrafer.Pojo.RestBean;
import com.github.pregrafer.Pojo.UserAccount;
import com.github.pregrafer.Service.InfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/info")
public class InfoController {
    @Resource
    UserMapper userMapper;
    @Resource
    InfoService infoService;

    @GetMapping("/get-info")
    public UserAccount getUserInfo(@RequestParam("username") String username) {
        return userMapper.findAccountByNameOrPhone(username);
    }

    @PostMapping("update-info")
    public RestBean<String> updateUserInfo(@RequestBody UserAccount userAccount) {
        if (infoService.updataUserInfo(userAccount))
            return RestBean.success("信息保存成功!");
        else
            return RestBean.failure(407, "信息保存失败!");
    }
}
