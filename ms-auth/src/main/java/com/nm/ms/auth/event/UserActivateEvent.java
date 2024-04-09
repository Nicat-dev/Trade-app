package com.nm.ms.auth.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserActivateEvent extends ApplicationEvent {

    private final String email;

    public UserActivateEvent(String email, Object source) {
        super(source);
        this.email = email;
    }

}
