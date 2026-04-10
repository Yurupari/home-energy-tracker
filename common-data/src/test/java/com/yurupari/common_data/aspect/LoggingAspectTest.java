package com.yurupari.common_data.aspect;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.yurupari.common_data.BaseUnitTest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoggingAspectTest extends BaseUnitTest {

    private LoggingAspect loggingAspect;
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        loggingAspect = new LoggingAspect();

        Logger logger = (Logger) LoggerFactory.getLogger(LoggingAspect.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    void loggableMethods() {
        assertDoesNotThrow(() -> loggingAspect.loggableMethods());
    }

    @Test
    void logBefore() {
        var joinPoint = mock(JoinPoint.class);
        var signature = mock(Signature.class);
        var args = new Object[] {"arg1", 123};

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("testMethod");
        when(joinPoint.getArgs()).thenReturn(args);

        assertDoesNotThrow(() -> loggingAspect.logBefore(joinPoint));

        ILoggingEvent logEvent = listAppender.list.getFirst();

        assertEquals(Level.INFO, logEvent.getLevel());
        assertTrue(logEvent.getFormattedMessage()
                .contains("Called service method: method=testMethod, args=[arg1, 123]"));
    }

    @Test
    void logAfterReturning() {
        var joinPoint = mock(JoinPoint.class);
        var signature = mock(Signature.class);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("testMethod");

        assertDoesNotThrow(() -> loggingAspect.logAfterReturning(joinPoint, "Success"));

        ILoggingEvent logEvent = listAppender.list.getFirst();

        assertEquals(Level.INFO, logEvent.getLevel());
        assertTrue(logEvent.getFormattedMessage()
                .contains("Service method returned: method=testMethod, result=Success"));
    }
}