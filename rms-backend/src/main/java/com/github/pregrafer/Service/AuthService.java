package com.github.pregrafer.Service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    boolean registerPost(String username, String password, String phone, String personid, String role);

    boolean resetPassword(String username, String phone, String personid, String newPassword);

    String getUserRoleByUserName(String username);
}
