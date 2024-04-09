package com.nm.ms.auth.security.jwt;

import com.nm.ms.auth.util.SecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final var token = resolveTokenAuthorizationHeader(request);
        if (token.isPresent() && tokenUtil.validateToken(token.get())) {
            final var authentication = tokenUtil.parseAuthentication(token.get());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private Optional<String> resolveTokenAuthorizationHeader(@NonNull HttpServletRequest request) {
        final var bearerToken = request.getHeader(SecurityUtils.AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(SecurityUtils.BEARER_TOKEN_PREFIX)) {
            return Optional.of(bearerToken.substring(SecurityUtils.BEARER_TOKEN_PREFIX.length()));
        }
        return Optional.empty();
    }

}
