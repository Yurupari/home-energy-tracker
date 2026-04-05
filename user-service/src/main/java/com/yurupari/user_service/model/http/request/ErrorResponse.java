package com.yurupari.user_service.model.http.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(
        HttpStatus httpStatus,
        @JsonFormat(pattern = "yyyyMMdd hh:mm:ss") LocalDateTime timeStamp,
        String message
) {
}
