package com.nm.ms.account.error;

import org.springframework.http.HttpStatus;

public enum Errors implements ErrorResponse {
    INVALID_CREDENTIALS(
            "invalid_credentials",
            "Invalid credentials",
            HttpStatus.UNAUTHORIZED
    ),
    RESOURCE_ID_NOT_FOUND(
            "resource_id_not_found",
            "Resource with id {id} not found",
            HttpStatus.NOT_FOUND
    ),
    INVALID_JWT_TOKEN(
            "invalid_jwt_token",
            "Invalid jwt token",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_REFRESH_TOKEN(
            "invalid_refresh_token",
            "Invalid refresh token",
            HttpStatus.UNPROCESSABLE_ENTITY
    ),
    PERMISSION_DENIED(
            "permission_denied",
            "You can only get your own user information",
            HttpStatus.FORBIDDEN
    ),
    UNAUTHORIZED("unauthorized", "Unauthorized", HttpStatus.UNAUTHORIZED),
    USER_ID_NOT_FOUND("user_id_not_found", "User with id {id} not found", HttpStatus.NOT_FOUND),
    MAIL_SEND_FAILED("mail_send_failed", "Mail send failed", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_VERIFICATION_TOKEN("invalid_verification_token", "Invalid verification token", HttpStatus.UNPROCESSABLE_ENTITY),
    USERNAME_NOT_FOUND("username_not_found", "User with username {username} not found", HttpStatus.NOT_FOUND),
    USER_NOT_ACTIVE("user_not_active", "User {key} is not active", HttpStatus.UNAUTHORIZED),
    EMAIL_NOT_FOUND("email_not_found", "User with email {email} not found", HttpStatus.NOT_FOUND);

    private final String key;
    private final String message;
    private final HttpStatus httpStatus;

    Errors(String key, String message, HttpStatus httpStatus) {
        this.key = key;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
