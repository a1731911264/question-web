package com.manji.edu.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfigurer extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        /*registry.addInterceptor(new AuthenticationInterceptor()).addPathPatterns("/**").excludePathPatterns("/auth/login", "/druid/*");*/
    }
}
