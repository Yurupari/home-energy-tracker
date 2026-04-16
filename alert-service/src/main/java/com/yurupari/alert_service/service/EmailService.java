package com.yurupari.alert_service.service;

public interface EmailService {

    boolean sendEmail(String to, String subject, String body);
}
