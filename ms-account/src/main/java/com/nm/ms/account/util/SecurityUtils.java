package com.nm.ms.account.util;


import com.nm.ms.account.security.CustomUserDetails;
import org.apache.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

public class SecurityUtils {

    public static final String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;
    public static final String BEARER_TOKEN_PREFIX = "Bearer ";

    public static Optional<CustomUserDetails> getCurrentUserDetails() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            return Optional.empty();
        }
        final var userDetails = (CustomUserDetails) authentication.getPrincipal();
        return Optional.of(userDetails);
    }

}
