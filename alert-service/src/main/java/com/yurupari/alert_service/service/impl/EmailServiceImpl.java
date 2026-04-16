package com.yurupari.alert_service.service.impl;

import com.yurupari.alert_service.service.EmailService;
import com.yurupari.common_data.annotation.Loggable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${email.from}")
    private String emailFrom;

    @Override
    public boolean sendEmail(
            String to,
            String subject,
            String body
    ) {
        var message = buildMessage(to, subject, body);

        try {
            javaMailSender.send(message);

            return true;
        } catch (MailException e) {
            log.error("Failed to send email: to={}", to, e);
            return false;
        }
    }

    private SimpleMailMessage buildMessage(
            String to,
            String subject,
            String body
    ) {
        var message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(emailFrom);
        message.setSubject(subject);
        message.setText(body);

        return message;
    }
}
