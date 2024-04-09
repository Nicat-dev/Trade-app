package com.nm.ms.auth.service.impl;

import com.nm.ms.auth.error.ApplicationException;
import com.nm.ms.auth.error.Errors;
import com.nm.ms.auth.event.UserActivateEvent;
import com.nm.ms.auth.model.enums.TokenType;
import com.nm.ms.auth.model.request.LoginRequest;
import com.nm.ms.auth.model.request.RefreshTokenRequest;
import com.nm.ms.auth.model.response.TokenPair;
import com.nm.ms.auth.security.SecurityProperties;
import com.nm.ms.auth.security.jwt.TokenUtil;
import com.nm.ms.auth.service.AuthService;
import com.nm.ms.auth.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenUtil tokenUtil;
    private final RedisUtil<Object> cache;
    private final SecurityProperties securityProperties;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final static String TOKEN_PAIR_STORE_PREFIX = "token_pair:";

    @Override
    public TokenPair login(LoginRequest request) {
        final var authentication = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return createAndSaveTokenPair(authentication);
    }

    @Override
    public TokenPair refresh(RefreshTokenRequest request) {
        tokenUtil.validateToken(request.refreshToken());
        final var authentication = tokenUtil.parseAuthentication(request.refreshToken());
        final var tokenPair = (TokenPair) cache.getValue(authentication.getName());
        if (Objects.isNull(tokenPair) || !Objects.equals(tokenPair.refreshToken(), request.refreshToken())) {
            throw new ApplicationException(Errors.INVALID_REFRESH_TOKEN);
        }
        return createAndSaveTokenPair(authentication);
    }

    @Override
    public void verifyEmail(String email, String token) {
        final var verificationEmailTokenKey = UserServiceImpl.VERIFICATION_TOKEN_STORE_PREFIX + email;
        final var verificationToken = (String) cache.getValue(verificationEmailTokenKey);
        if (Objects.isNull(verificationToken) || !verificationToken.equals(token)) {
            throw new ApplicationException(Errors.INVALID_VERIFICATION_TOKEN);
        }
        applicationEventPublisher.publishEvent(new UserActivateEvent(email, this));
        cache.delete(verificationEmailTokenKey);
        log.info("Email verified for: {}", email);
    }

    private TokenPair createAndSaveTokenPair(Authentication authentication) {
        final var tokenPair = new TokenPair(
                tokenUtil.createToken(authentication, TokenType.ACCESS),
                tokenUtil.createToken(authentication, TokenType.REFRESH)
        );
        cache.putValue(TOKEN_PAIR_STORE_PREFIX + authentication.getName(), tokenPair,
                securityProperties.getRefreshTokenValidityMinutes(), TimeUnit.MINUTES);
        return tokenPair;
    }

}
