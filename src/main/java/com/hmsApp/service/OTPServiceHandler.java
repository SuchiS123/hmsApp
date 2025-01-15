package com.hmsApp.service;

import com.hmsApp.util.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OTPServiceHandler {

    @Autowired
    private TwilioService twilioService;

    @Autowired
    private OTPService otpService;

    private String generatedOtp;

    public String sendOtp(String mobile) {
        // Generate OTP
        generatedOtp = otpService.generateOTP();

        // Send OTP via SMS
        String body = "Your OTP is: " + generatedOtp;
        String sid = twilioService.sendSms(mobile,body);  // Only passing 2 parameters

        // Return Twilio SID to track the message if needed
        return sid;
    }

    public boolean verifyOtp(String enteredOtp) {
        // Check if the entered OTP matches the generated OTP
        return generatedOtp.equals(enteredOtp);
    }
}
