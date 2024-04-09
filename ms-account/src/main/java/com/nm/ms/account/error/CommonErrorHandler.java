package com.nm.ms.account.error;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.nm.ms.account.util.MessageSourceUtils.getMessage;

@RestControllerAdvice
@RequiredArgsConstructor
public class CommonErrorHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<CommonErrorResponse> handle(ApplicationException ex, WebRequest request) {

        var errorBody = CommonErrorResponse.builder()
                .error(ex.getErrorResponse().getHttpStatus().getReasonPhrase())
                .message(ex.getLocalizedMessage(LocaleContextHolder.getLocale(), messageSource))
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .status(ex.getErrorResponse().getHttpStatus().value())
                .build();

        return new ResponseEntity<>(errorBody, ex.getErrorResponse().getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        var message = getMessage("validation.methodArgumentNotValid", Map.of(), messageSource);
        var httpStatus = HttpStatus.valueOf(status.value());

        Map<String, Object> details = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fieldError -> details.put(fieldError.getField(), fieldError.getDefaultMessage()));

        var errorBody = CommonErrorResponse.builder()
                .error(httpStatus.getReasonPhrase())
                .message(message)
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .status(httpStatus.value())
                .details(details)
                .build();

        return new ResponseEntity<>(errorBody, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex,
                                                             Object body,
                                                             @NonNull HttpHeaders headers,
                                                             @NonNull HttpStatusCode statusCode,
                                                             @NonNull WebRequest request) {
        var httpStatus = HttpStatus.valueOf(statusCode.value());
        var errorBody = CommonErrorResponse.builder()
                .error(httpStatus.getReasonPhrase())
                .message(ex.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .timestamp(LocalDateTime.now())
                .status(httpStatus.value())
                .build();

        return new ResponseEntity<>(errorBody, headers, httpStatus);
    }

}
