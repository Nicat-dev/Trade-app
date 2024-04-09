package com.nm.ms.auth.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class VerificationEmailEvent extends ApplicationEvent {

    private final String email;
    private final String token;

    public VerificationEmailEvent(String email, String token, Object source) {
        super(source);
        this.email = email;
        this.token = token;
    }

}
