package com.hmsApp.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OTPService {

    private static final int OTP_LENGTH = 6;

    public static String generateOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));  // Appending random digits
        }
        return otp.toString();
    }

}
