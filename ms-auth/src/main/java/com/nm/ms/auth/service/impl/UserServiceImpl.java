package com.nm.ms.auth.service.impl;

import com.nm.ms.auth.error.ApplicationException;
import com.nm.ms.auth.error.Errors;
import com.nm.ms.auth.event.VerificationEmailEvent;
import com.nm.ms.auth.mapper.UserMapper;
import com.nm.ms.auth.model.domain.User;
import com.nm.ms.auth.model.request.RegisterRequest;
import com.nm.ms.auth.model.response.UserResponse;
import com.nm.ms.auth.repository.UserRepository;
import com.nm.ms.auth.service.UserService;
import com.nm.ms.auth.util.RedisUtil;
import com.nm.ms.auth.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RedisUtil<String> cache;
    public static final String VERIFICATION_TOKEN_STORE_PREFIX = "verification_token:";

    @Override
    @Transactional
    public UserResponse registerUser(RegisterRequest request) {
        // todo: check username and email uniqueness
        final var user = mapper.toUser(request);
        final var token = UUID.randomUUID().toString();
        cache.putValue(VERIFICATION_TOKEN_STORE_PREFIX + user.getEmail(), token, 1, TimeUnit.HOURS);
        applicationEventPublisher.publishEvent(new VerificationEmailEvent(user.getEmail(), token, this));
        return mapper.toUserResponse(repository.save(user));
    }

    @Override
    public UserResponse getUser(Long id) {
        SecurityUtils.getCurrentUserId().orElseThrow(() -> new ApplicationException(Errors.UNAUTHORIZED));
        SecurityUtils.getCurrentUserId().ifPresent(userId -> {
            if (!userId.equals(id)) {
                throw new ApplicationException(Errors.PERMISSION_DENIED);
            }
        });
        return mapper.toUserResponse(findUser(id));
    }

    private User findUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApplicationException(Errors.USER_ID_NOT_FOUND, Map.of("id", id)));
    }

}
