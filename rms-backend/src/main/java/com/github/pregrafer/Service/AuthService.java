package com.github.pregrafer.Service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    boolean registerPost(String username, String password, String phone, String personid, String role);
}
