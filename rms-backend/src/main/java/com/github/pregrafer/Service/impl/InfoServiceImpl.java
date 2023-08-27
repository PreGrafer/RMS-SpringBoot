package com.github.pregrafer.Service.impl;

import com.github.pregrafer.Mapper.UserMapper;
import com.github.pregrafer.Pojo.UserAccount;
import com.github.pregrafer.Service.InfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {
    @Resource
    UserMapper userMapper;

    @Override
    public boolean updataUserInfo(UserAccount userAccount) {
        return userMapper.updataUserAccountInfo(userAccount);
    }
}
