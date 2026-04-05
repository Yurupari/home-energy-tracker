package com.yurupari.user_service.error;

import com.yurupari.user_service.BaseUnitTest;
import com.yurupari.user_service.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ErrorHandlerTest extends BaseUnitTest {

    @InjectMocks
    private ErrorHandler errorHandler;

    @Test
    void handleUserNotFoundException() {
        var response = errorHandler.handleUserNotFoundException(new UserNotFoundException(1L));

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}