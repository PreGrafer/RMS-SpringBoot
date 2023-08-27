package com.github.pregrafer.Service.impl;

import com.github.pregrafer.Mapper.RegisterMapper;
import com.github.pregrafer.Mapper.UserMapper;
import com.github.pregrafer.Pojo.RegisterAccount;
import com.github.pregrafer.Service.RegisterService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Resource
    UserMapper userMapper;
    @Resource
    RegisterMapper registerMapper;

    @Override
    public boolean passRegister(RegisterAccount registerAccount) {
        return userMapper.insertUserAccount(registerAccount) && registerMapper.deleteByUsername(registerAccount.getUsername());
    }

    @Override
    public boolean rejectRegister(String username) {
        return registerMapper.deleteByUsername(username);
    }
}
