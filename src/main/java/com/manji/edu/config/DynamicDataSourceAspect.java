package com.manji.edu.config;

import com.manji.edu.annotation.TargetDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DynamicDataSourceAspect {
    private Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);
    @Around("execution(public * com.demo.demo.service..*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method targetMethod = methodSignature.getMethod();
        if (targetMethod.isAnnotationPresent(TargetDataSource.class)) {
            String targetDataSource = targetMethod.getAnnotation(TargetDataSource.class).dataSource();
            logger.info("数据源动态选择=>选择的数据源是：" + targetDataSource);
            DynamicDataSourceHolder.setDataSource(targetDataSource);
        }
        Object result = pjp.proceed();// 执行方法
        DynamicDataSourceHolder.clearDataSource();
        return result;
    }
}
