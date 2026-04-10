package com.yurupari.common_data.aspect;

import com.yurupari.common_data.BaseUnitTest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExecutionTimeAspectTest extends BaseUnitTest {

    private ExecutionTimeAspect executionTimeAspect;

    @BeforeEach
    void setUp() {
        executionTimeAspect = new ExecutionTimeAspect();
    }

    @Test
    void trackTimeMethods() {
        assertDoesNotThrow(() -> executionTimeAspect.trackTimeMethods());
    }

    @Test
    void logExecutionTime() throws Throwable {
        var joinPoint = mock(ProceedingJoinPoint.class);
        var signature = mock(Signature.class);

        when(joinPoint.proceed()).thenReturn("Success");
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("testMethod");

        var response = executionTimeAspect.logExecutionTime(joinPoint);

        assertNotNull(response);
        assertEquals("Success", response);
        verify(joinPoint, times(1)).proceed();
        verify(signature, times(1)).getName();
    }
}