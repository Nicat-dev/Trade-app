package com.nm.ms.auth.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Map;

@Slf4j
public final class MessageSourceUtils {

    private MessageSourceUtils() {
    }

    public static String getMessage(String key, Map<String, Object> arguments, MessageSource messageSource) {
        try {
            String localizedMessage = messageSource.getMessage(key, new Object[]{}, LocaleContextHolder.getLocale());
            return arguments.isEmpty() ? localizedMessage :
                    StringSubstitutor.replace(localizedMessage, arguments, "{", "}");
        } catch (NoSuchMessageException exception) {
            log.warn("Please consider adding localized message for key {} and locale {}",
                    key, LocaleContextHolder.getLocale());
        }
        return key;
    }

}
