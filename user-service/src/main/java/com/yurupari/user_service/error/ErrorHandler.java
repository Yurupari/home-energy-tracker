package com.yurupari.user_service.error;

import com.yurupari.user_service.exception.UserNotFoundException;
import com.yurupari.common_data.model.http.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        var errorResponse = buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    private ErrorResponse buildErrorResponse(HttpStatus httpStatus, String errorMessage) {
        return new ErrorResponse(
                httpStatus,
                LocalDateTime.now(),
                errorMessage
        );
    }
}
