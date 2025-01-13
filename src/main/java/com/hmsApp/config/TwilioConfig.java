package com.hmsApp.config;


import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {
//
//    @Bean
//    public void init()
//    {
//        Twilio.init("ACe312b9d04ceb67a5de34e257845bc4c6", "6756ff6e53313ec1bdb7a5cb3232374d");
//    }

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Bean
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }
}
