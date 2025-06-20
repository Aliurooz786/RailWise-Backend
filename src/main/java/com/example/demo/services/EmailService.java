package com.example.demo.services;

import jakarta.mail.MessagingException;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendBookingConfirmation(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("a.aliurooz786@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendEmailWithAttachment(String to, String subject, String body, byte[] pdfBytes, String filename) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("a.aliurooz786@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            helper.addAttachment(filename, new ByteArrayDataSource(pdfBytes, "application/pdf"));

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email with attachment: " + e.getMessage());
        }
    }
}