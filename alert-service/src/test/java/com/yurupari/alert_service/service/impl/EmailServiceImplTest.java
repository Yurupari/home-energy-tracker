package com.yurupari.alert_service.service.impl;

import com.yurupari.alert_service.BaseUnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmailServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    void sendEmail_Success() {
        var response = emailService.sendEmail("to@email.com", "TEST_SUBJECT", "TEST_BODY");

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));

        assertTrue(response);
    }

    @Test
    void sendEmail_Fail() {
        doThrow(new MailSendException("SMTP server connection failed"))
                .when(javaMailSender).send(any(SimpleMailMessage.class));

        var response = emailService.sendEmail("to@email.com", "TEST_SUBJECT", "TEST_BODY");

        assertFalse(response);
    }
}