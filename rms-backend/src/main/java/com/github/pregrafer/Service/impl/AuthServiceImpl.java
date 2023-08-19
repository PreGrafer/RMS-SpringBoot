package com.github.pregrafer.Service.impl;

import com.github.pregrafer.Mapper.UserMapper;
import com.github.pregrafer.Entity.Account;
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
    UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new UsernameNotFoundException("用户名不能为空");
        }
        Account account = mapper.findAccountByNameOrPhone(username);
        if (account == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRole())
                .build();
    }

    @Override
    public boolean registerPost(String username, String password, String phone, String personid, String role) {
        if (mapper.user_accountCheck(username, phone, personid) > 0 || mapper.register_listCheck(username, phone, personid) > 0) {
            return false;
        }
        return mapper.registerPost(username, (new BCryptPasswordEncoder()).encode(password), phone, personid, role);
    }
}
