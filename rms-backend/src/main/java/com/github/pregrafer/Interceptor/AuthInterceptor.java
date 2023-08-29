package com.github.pregrafer.Interceptor;

import com.github.pregrafer.Controller.AccountController;
import com.github.pregrafer.Entity.SimpleUserInfo;
import com.github.pregrafer.Mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Resource
    UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SimpleUserInfo simpleUserInfo = userMapper.getUserInfo(user.getUsername());
        request.getSession().setAttribute("userInfo", simpleUserInfo);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
