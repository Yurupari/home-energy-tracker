package com.yurupari.user_service.aspect;

import com.yurupari.user_service.BaseUnitTest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExecutionTimeAspectTest extends BaseUnitTest {

    @InjectMocks
    private ExecutionTimeAspect executionTimeAspect;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    @Test
    void logExecutionTime() throws Throwable {
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getName()).thenReturn("userService.createUser()");
        when(joinPoint.proceed()).thenReturn("Test Result");

        var result = executionTimeAspect.logExecutionTime(joinPoint);

        assertEquals("Test Result", result);
        verify(joinPoint).proceed();
    }
}
