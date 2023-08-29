package com.github.pregrafer.Service.impl;

import com.github.pregrafer.Mapper.RegisterMapper;
import com.github.pregrafer.Mapper.UserMapper;
import com.github.pregrafer.Entity.UserAccount;
import com.github.pregrafer.Service.AuthService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    UserMapper userMapper;
    @Resource
    RegisterMapper registerMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new UsernameNotFoundException("用户名不能为空");
        }
        UserAccount userAccount = userMapper.findAccountByNameOrPhone(username);
        if (userAccount == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return User
                .withUsername(userAccount.getUsername())
                .password(userAccount.getPassword())
                .roles(userAccount.getRole())
                .build();
    }

    @Override
    public boolean registerPost(String username, String password, String phone, String personid, String role) {
        if (userMapper.user_accountCheck(username, phone, personid) > 0 || userMapper.register_listCheck(username, phone, personid) > 0) {
            return false;
        }
        return registerMapper.registerPost(username, (new BCryptPasswordEncoder()).encode(password), phone, personid, role);
    }

    @Override
    public boolean resetPassword(String username, String newPassword, String phone, String personid) {
        if (userMapper.user_accountCheck(username, phone, personid) > 0) {
            String encryptedPassword = (new BCryptPasswordEncoder()).encode(newPassword);
            return userMapper.resetPassword(username, encryptedPassword, phone, personid);
        }
        return false;
    }

    @Override
    public String getUserRoleByUserName(String username) {
        return userMapper.getUseRoleByUserName(username);
    }
}
