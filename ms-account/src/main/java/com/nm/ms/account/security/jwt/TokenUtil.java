package com.nm.ms.account.security.jwt;

import com.nm.ms.auth.error.ApplicationException;
import com.nm.ms.auth.error.Errors;
import com.nm.ms.auth.model.enums.TokenType;
import com.nm.ms.auth.security.CustomUserDetails;
import com.nm.ms.auth.security.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TokenUtil {

    private final SecurityProperties securityProperties;
    private SecretKey key;

    @PostConstruct
    private void init() {
        byte[] keyBytes = Decoders.BASE64.decode(securityProperties.getSecret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication, TokenType tokenType) {
        CustomUserDetails customUserPrincipal = (CustomUserDetails) authentication.getPrincipal();

        Integer tokenValidityInSeconds = (tokenType == TokenType.ACCESS) ?
                securityProperties.getAccessTokenValidityMinutes() :
                securityProperties.getRefreshTokenValidityMinutes();

        LocalDateTime validity = LocalDateTime.now().plusMinutes(tokenValidityInSeconds);

        return Jwts.builder()
                .subject(customUserPrincipal.getUsername())
                .claim(TokenKey.TOKEN_TYPE, tokenType)
                .claim(TokenKey.ACTIVE, customUserPrincipal.isActive())
                .claim(TokenKey.EMAIL, customUserPrincipal.getEmail())
                .claim(TokenKey.USER_ID, customUserPrincipal.getId())
                .signWith(key)
                .expiration(Date.from(validity.atZone(ZoneId.systemDefault()).toInstant()))
                .compact();
    }

    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public TokenType getTokenType(String token) {
        return extractClaim(token, claims -> TokenType.valueOf((String) claims.get(TokenKey.TOKEN_TYPE)));
    }

    public String getEmail(String token) {
        return extractClaim(token, claims -> (String) claims.get(TokenKey.EMAIL));
    }

    public boolean isActive(String token) {
        return extractClaim(token, claims -> (boolean) claims.get(TokenKey.ACTIVE));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Authentication parseAuthentication(String token) {
        final var username = getUsername(token);
        final var email = getEmail(token);
        final var active = isActive(token);
        final var tokenType = getTokenType(token);
        final var userDetails = CustomUserDetails.builder()
                .username(username)
                .email(email)
                .active(active)
                .tokenType(tokenType)
                .build();
        return new UsernamePasswordAuthenticationToken(userDetails, null, Collections.emptyList());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            throw new ApplicationException(Errors.INVALID_JWT_TOKEN);
        }
    }
}
