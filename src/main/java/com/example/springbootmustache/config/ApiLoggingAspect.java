package com.example.springbootmustache.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ApiLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(ApiLoggingAspect.class);

    @Pointcut("execution(* com.example.springbootmustache.nakji.api.service..*(..))")  // 대상 메서드 지정
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String arguments = Arrays.toString(joinPoint.getArgs());
        logger.info("========== Method start: {}, Arguments: {} ==========", joinPoint.getSignature().getName(), arguments);

        try {
            Object result = joinPoint.proceed(); // 대상 메서드 실행
            return result;
        } finally {
            double timeTaken = (System.currentTimeMillis() - startTime)/1000.0;
            logger.info("//======== Method end: {}, {}s ========//", joinPoint.getSignature().getName(), timeTaken);
        }
    }
}