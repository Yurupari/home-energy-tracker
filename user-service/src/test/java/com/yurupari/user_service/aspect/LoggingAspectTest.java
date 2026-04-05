package com.yurupari.user_service.aspect;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.yurupari.user_service.BaseUnitTest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class LoggingAspectTest extends BaseUnitTest {

    @InjectMocks
    private LoggingAspect loggingAspect;

    @Mock
    private JoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(LoggingAspect.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @AfterEach
    void tearDown() {
        Logger logger = (Logger) LoggerFactory.getLogger(LoggingAspect.class);
        logger.detachAppender(listAppender);
    }

    @Test
    void logBefore() {
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getName()).thenReturn("getUser");
        when(joinPoint.getArgs()).thenReturn(new Object[] {"1"});

        loggingAspect.logBefore(joinPoint);

        assertTrue(listAppender.list.stream()
                .anyMatch(event -> event.getLevel().equals(Level.INFO)
                        && event.getFormattedMessage().contains("Called service method: method=getUser, args=[1]")));
    }

    @Test
    void logAfterReturning() {
        var result = "NEW_USER";

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getName()).thenReturn("createUser");

        loggingAspect.logAfterReturning(joinPoint, result);

        assertTrue(listAppender.list.stream()
                .anyMatch(event -> event.getLevel().equals(Level.INFO)
                        && event.getFormattedMessage().contains("Service method returned: method=createUser, result=NEW_USER")));
    }
}
