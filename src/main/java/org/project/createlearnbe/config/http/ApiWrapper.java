package org.project.createlearnbe.config.http;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiWrapper<T> {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private T data;

    public static <T> ApiWrapper<T> success(T data) {
        return ApiWrapper.<T>builder()
                .status(200)
                .message("Success")
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }

    public static <T> ApiWrapper<T> error(String message, int status) {
        return ApiWrapper.<T>builder()
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .data(null)
                .build();
    }
}

