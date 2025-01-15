package com.hmsApp.service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Value("${twilio.phoneNumber}")
    private String fromPhoneNumber;

    public String sendSms(String to, String body) {
        Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(fromPhoneNumber),
                body
        ).create();

        return message.getSid();
    }

}
