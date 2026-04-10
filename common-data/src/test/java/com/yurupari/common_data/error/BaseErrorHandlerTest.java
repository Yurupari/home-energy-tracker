package com.yurupari.common_data.error;

import com.yurupari.common_data.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BaseErrorHandlerTest extends BaseUnitTest {

    private BaseErrorHandler baseErrorHandler;

    @BeforeEach
    void setUp() {
        baseErrorHandler = new BaseErrorHandler() {};
    }

    @Test
    void handleGeneralException() {
        var response = baseErrorHandler.handleGeneralException(new Exception("Test exception"));

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test exception", response.getBody().message());
    }
}
