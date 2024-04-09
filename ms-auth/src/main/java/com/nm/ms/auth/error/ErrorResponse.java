package com.nm.ms.auth.error;

import org.springframework.http.HttpStatus;

public interface ErrorResponse {
    String getKey();

    String getMessage();

    HttpStatus getHttpStatus();
}
