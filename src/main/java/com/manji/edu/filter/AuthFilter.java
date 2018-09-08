package com.manji.edu.filter;

import com.manji.edu.utils.IpUtil;
import com.manji.edu.utils.JwtUtils;
import com.manji.edu.utils.ResponseUtil;
import com.manji.edu.utils.ThreadLocalUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*",filterName = "authFilter")
public class AuthFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        LOGGER.info("用户状态认证=>认证开始，IP={}", IpUtil.getRomoteAddr(request));
        // 获取指定头信息Authentication 头信息
        if (request.getMethod().equals("OPTIONS")){
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,PUT,DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "accept,x-requested-with,Content-Type,authentication");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setStatus(204);
            return;
        }
        String requestURI = request.getRequestURI();
        if (requestURI.indexOf("login") > -1 || requestURI.indexOf("logout") > -1 || requestURI.indexOf("image-code") > -1) {
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        try {
            String authorization = request.getHeader("authentication");
            if (StringUtils.isBlank(authorization)) throw new RuntimeException();
            String userId = JwtUtils.getUserId(authorization, "userId");
            ThreadLocalUtil.setUserId(userId);
            ThreadLocalUtil.setToken(authorization);
            LOGGER.info("用户状态认证=>认证成功，userId={}", userId);
            filterChain.doFilter(servletRequest,servletResponse);
        } catch (Exception e) {
            LOGGER.info("用户状态认证=>认证失败，登陆状态失效！" );
            ResponseUtil.resourceUnauthorized(response);
        }
    }

    @Override
    public void destroy() {

    }
}
