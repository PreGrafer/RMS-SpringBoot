package com.github.pregrafer.Service.impl;

import com.github.pregrafer.Mapper.UserMapper;
import com.github.pregrafer.Service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    UserMapper userMapper;

    @Override
    public boolean deleteUser(int userid) {
        return userMapper.deleteUserById(userid);
    }
}
