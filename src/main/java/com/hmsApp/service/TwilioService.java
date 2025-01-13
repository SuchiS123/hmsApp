package com.hmsApp.service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    public String sendSms(String to, String from, String body) {
        Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(from),
                body
        ).create();

        return message.getSid();
    }

}
