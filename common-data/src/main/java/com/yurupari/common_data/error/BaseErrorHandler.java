package com.yurupari.common_data.error;

import com.yurupari.common_data.model.http.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public abstract class BaseErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        var errorResponse = buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ErrorResponse buildErrorResponse(HttpStatus httpStatus, String errorMessage) {
        return new ErrorResponse(
                httpStatus,
                LocalDateTime.now(),
                errorMessage
        );
    }
}
