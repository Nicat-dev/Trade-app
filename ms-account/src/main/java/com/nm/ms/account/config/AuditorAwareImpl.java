package com.nm.ms.account.config;

import com.nm.ms.account.security.CustomUserDetails;
import com.nm.ms.account.util.SecurityUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return SecurityUtils.getCurrentUserDetails().map(CustomUserDetails::getUsername);
    }

}