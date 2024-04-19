package com.nm.ms.auth.service.impl;

import com.nm.ms.auth.error.ApplicationException;
import com.nm.ms.auth.error.Errors;
import com.nm.ms.auth.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendEmail(String to, String subject, String body, boolean isHtml) {
        final var helper = new MimeMessageHelper(mailSender.createMimeMessage());

        try {
            helper.setFrom(from, "Nicat");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, isHtml);
        } catch (UnsupportedEncodingException e) {
            throw new ApplicationException(Errors.MAIL_SEND_FAILED, e);
        }

        mailSender.send(helper.getMimeMessage());
    }

}
