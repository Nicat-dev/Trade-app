package com.nm.ms.auth.util;


import com.nm.ms.auth.security.CustomUserDetails;
import org.apache.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

public class SecurityUtils {

    public static final String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;
    public static final String BEARER_TOKEN_PREFIX = "Bearer ";

    public static String getCurrentUsername() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ? null : authentication.getName();
    }

    public static Optional<Long> getCurrentUserId() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            return Optional.empty();
        }
        final var userDetails = (CustomUserDetails) authentication.getPrincipal();
        return Optional.of(userDetails.getId());
    }

}
