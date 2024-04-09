package com.nm.ms.auth.event.listener;

import com.nm.ms.auth.event.VerificationEmailEvent;
import com.nm.ms.auth.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class VerificationEmailEventListener implements ApplicationListener<VerificationEmailEvent> {

    private final MailService mailService;

    @Override
    @EventListener
    public void onApplicationEvent(VerificationEmailEvent event) {
        final var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final var confirmationLink = format("http://localhost:8080/api/v1/auth/verify-email?email=%s&token=%s",
                event.getEmail(), event.getToken());
        final var expirationTime = LocalDateTime.now().plusHours(1);
        mailService.sendEmail(
                event.getEmail(),
                "Verification email",
                format("Please confirm your email by clicking on this %s by %s",
                        confirmationLink, dateTimeFormatter.format(expirationTime)),
                true);
    }

}
