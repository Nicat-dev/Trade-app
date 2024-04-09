package com.nm.ms.auth.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private String secret;
    private Integer accessTokenValidityMinutes;
    private Integer refreshTokenValidityMinutes;

}
