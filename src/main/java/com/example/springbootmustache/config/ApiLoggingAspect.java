package com.example.springbootmustache.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(ApiLoggingAspect.class);

    @Pointcut("execution(* com.example.springbootmustache.nakji.api..*(..))")  // 대상 메서드 지정
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        logger.info("========== Method start: {} ==========", joinPoint.getSignature().getName());

        try {
            Object result = joinPoint.proceed(); // 대상 메서드 실행
            return result;
        } finally {
            long timeTaken = System.currentTimeMillis() - startTime;
            logger.info("//======== Method end: {} ========//", joinPoint.getSignature().getName());
        }
    }
}