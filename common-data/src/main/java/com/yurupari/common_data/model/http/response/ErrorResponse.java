package com.yurupari.common_data.model.http.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(
        HttpStatus httpStatus,
        @JsonFormat(pattern = "yyyy.MM.dd hh:mm:ss") LocalDateTime timeStamp,
        String message
) {
}
