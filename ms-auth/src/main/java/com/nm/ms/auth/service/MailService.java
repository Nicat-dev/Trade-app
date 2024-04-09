package com.nm.ms.auth.service;

public interface MailService {

    void sendEmail(String to, String subject, String body, boolean isHtml);

}
