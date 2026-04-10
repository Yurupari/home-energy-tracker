package com.yurupari.common_data.aspect;

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

    @Pointcut("@annotation(com.yurupari.common_data.annotation.TrackTime) || @within(com.yurupari.common_data.annotation.TrackTime)")
    public void trackTimeMethods() {}

    @Around("trackTimeMethods()")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;

        log.info("Method {} executed in {} ms",
                proceedingJoinPoint.getSignature().getName(),
                executionTime);

        return proceed;
    }
}
