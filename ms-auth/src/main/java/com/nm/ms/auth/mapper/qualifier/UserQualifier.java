package com.nm.ms.auth.mapper.qualifier;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserQualifier {

    private final PasswordEncoder passwordEncoder;

    @Named("encodePassword")
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
