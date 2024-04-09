package com.nm.ms.auth.security;

import com.nm.ms.auth.error.ApplicationException;
import com.nm.ms.auth.error.Errors;
import com.nm.ms.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("Loading user by username: {}", username);
        final var user = repository.findByUsername(username)
                .orElseThrow(() -> new ApplicationException(Errors.USERNAME_NOT_FOUND, Map.of("username", username)));
        if (!user.getActive()) throw new ApplicationException(Errors.USER_NOT_ACTIVE, Map.of("key", username));
        return CustomUserDetails.builder()
                .username(user.getUsername())
                .active(user.getActive())
                .authorities(Collections.emptyList())
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

}
