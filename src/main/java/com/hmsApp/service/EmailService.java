package com.hmsApp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    // Method to send email
    public void sendThankYouEmail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Thank you for choosing our platform!");
        message.setText("Dear User,\n\nThank you for signing up on our platform. We are excited to have you on board!");

        javaMailSender.send(message);
    }

    // Method to send thank you email for login
    public void sendThankYouForLoginEmail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Thank you for logging in!");
        message.setText("Dear User,\n\nThank you for logging in. We appreciate your visit to our platform!");
        javaMailSender.send(message);
    }

    public void sendThankYouEmailWithAttachment(String toEmail, String pdfPath) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart message

        helper.setTo(toEmail);
        helper.setSubject("Thank you for booking with us!");
        helper.setText("Dear User,\n\nThank you for booking with us. Please find your booking confirmation attached.");

        // Attach PDF
        File pdf = new File(pdfPath);
        helper.addAttachment("BookingConfirmation.pdf", pdf);

        javaMailSender.send(message);
    }

}

