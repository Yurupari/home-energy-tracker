package com.yurupari.alert_service.service.impl;

import com.yurupari.alert_service.model.entity.Alert;
import com.yurupari.alert_service.repository.AlertRepository;
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

    private final AlertRepository alertRepository;

    @Value("${email.from}")
    private String emailFrom;

    @Override
    public void sendEmail(
            String to,
            String subject,
            String body,
            Long userId
    ) {
        var message = buildMessage(to, subject, body);

        try {
            javaMailSender.send(message);

            final var alertSent = Alert.builder()
                    .sent(true)
                    .userId(userId)
                    .build();

            alertRepository.saveAndFlush(alertSent);
        } catch (MailException e) {
            log.error("Failed to send email: to={}", to, e);

            final var alertNotSent = Alert.builder()
                    .sent(false)
                    .userId(userId)
                    .build();

            alertRepository.saveAndFlush(alertNotSent);
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
