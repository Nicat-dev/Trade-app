package com.nm.ms.auth.event.listener;

import com.nm.ms.auth.error.ApplicationException;
import com.nm.ms.auth.error.Errors;
import com.nm.ms.auth.event.UserActivateEvent;
import com.nm.ms.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserActivateEventListener implements ApplicationListener<UserActivateEvent> {

    private final UserRepository repository;

    @Override
    @TransactionalEventListener
    public void onApplicationEvent(UserActivateEvent event) {
        final var user = repository.findByEmail(event.getEmail())
                .orElseThrow(() -> new ApplicationException(Errors.EMAIL_NOT_FOUND, Map.of("email", event.getEmail())));
        user.setActive(true);
        repository.save(user);
    }

}

