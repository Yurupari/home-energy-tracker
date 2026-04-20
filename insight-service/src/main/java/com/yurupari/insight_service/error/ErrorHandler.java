package com.yurupari.insight_service.error;

import com.yurupari.common_data.error.BaseErrorHandler;
import com.yurupari.common_data.model.http.response.ErrorResponse;
import com.yurupari.insight_service.exception.PromptLoadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends BaseErrorHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlePromptLoadException(PromptLoadException e) {
        var errorResponse = buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
