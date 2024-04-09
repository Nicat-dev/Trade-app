package com.nm.ms.account.error;

import org.springframework.http.HttpStatus;

public interface ErrorResponse {
    String getKey();

    String getMessage();

    HttpStatus getHttpStatus();
}
