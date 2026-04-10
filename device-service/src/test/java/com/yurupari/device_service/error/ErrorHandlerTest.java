package com.yurupari.device_service.error;

import com.yurupari.device_service.BaseUnitTest;
import com.yurupari.device_service.exception.DeviceNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ErrorHandlerTest extends BaseUnitTest {

    @InjectMocks
    private ErrorHandler errorHandler;

    @Test
    void handleDeviceNotFoundException() {
        var response = errorHandler.handleDeviceNotFoundException(new DeviceNotFoundException(1L));

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}