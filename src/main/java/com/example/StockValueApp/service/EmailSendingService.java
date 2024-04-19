package com.example.StockValueApp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class EmailSendingService{

    private final JavaMailSender mailSender;

    public void sendEmail(final String toEmail) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("abadjanovas@gmail.com");
        message.setTo(toEmail);
        message.setText("Thank you for joining! Your account has been created successfully.");
        message.setSubject("Stock value app");

        mailSender.send(message);
        log.info("Mail sent successfully.");
    }

}
