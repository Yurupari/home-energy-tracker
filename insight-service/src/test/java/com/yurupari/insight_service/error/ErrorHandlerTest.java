package com.yurupari.insight_service.error;

import com.yurupari.insight_service.BaseUnitTest;
import com.yurupari.insight_service.exception.PromptLoadException;
import com.yurupari.insight_service.model.enums.InsightType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ErrorHandlerTest extends BaseUnitTest {

    @InjectMocks
    private ErrorHandler errorHandler;

    @Test
    void handlePromptLoadException() {
        var response = errorHandler.handlePromptLoadException(new PromptLoadException(InsightType.OVERVIEW));

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}