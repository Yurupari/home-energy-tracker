package com.yurupari.user_service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {

    @Pointcut("execution(* com.yurupari.user_service.controller.*.*(..))")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;

        log.info("Method {} executed in {} ms", proceedingJoinPoint.getSignature().getName(), executionTime);
        return proceed;
    }

}
