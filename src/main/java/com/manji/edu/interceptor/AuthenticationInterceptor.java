package com.manji.edu.interceptor;


import com.manji.edu.utils.IpUtil;
import com.manji.edu.utils.JwtUtils;
import com.manji.edu.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        LOGGER.info("用户状态认证=>认证开始，IP={}", IpUtil.getRomoteAddr(request));
        // 获取指定头信息Authentication 头信息
        String authorization = request.getHeader("token");
        try {
            String userId = JwtUtils.getUserId(authorization, "userId");
            LOGGER.info("用户状态认证=>认证成功，userId={}", userId);
        } catch (Exception e) {
            LOGGER.info("用户状态认证=>认证失败，登陆状态失效！" );
            ResponseUtil.resourceUnauthorized(response);
            return false;
        }
        return true;
    }
}
