package com.yurupari.alert_service.service;

public interface EmailService {

    void sendEmail(String to, String subject, String body, Long userId);
}
