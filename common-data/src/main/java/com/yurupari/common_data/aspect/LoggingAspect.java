package com.yurupari.common_data.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("@annotation(com.yurupari.common_data.annotation.Loggable) || @within(com.yurupari.common_data.annotation.Loggable)")
    public void loggableMethods() {}

    @Before("loggableMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Called service method: method={}, args={}",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "loggableMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Service method returned: method={}, result={}",
                joinPoint.getSignature().getName(),
                result);
    }
}
