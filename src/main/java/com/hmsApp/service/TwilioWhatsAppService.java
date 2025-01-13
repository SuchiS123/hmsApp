package com.hmsApp.service;

import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioWhatsAppService {

    @Value("${twilio.whatsapp.from}")
    private String fromWhatsApp;

    public String sendWhatsAppMessage(String to, String body) {
        try {
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber("whatsapp:" + to), // Recipient's phone number (must include "whatsapp:" prefix)
                            new com.twilio.type.PhoneNumber("whatsapp:" + fromWhatsApp), // Your Twilio WhatsApp number (must include "whatsapp:" prefix)
                            body) // The message body
                    .create();

            return "Message sent: " + message.getSid();
        } catch (Exception e) {
            return "Failed to send message: " + e.getMessage();
        }
    }
}
