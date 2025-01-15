package com.hmsApp.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class OTPService {

    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRY_TIME_MINUTES = 1; // OTP expiry time in minutes
    private static String otp;
    private static LocalDateTime otpGeneratedTime;

    public static String generateOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));  // Appending random digits
        }
        return otp.toString();
    }

    // Method to check if OTP is expired
    public static boolean isOtpExpired() {
        if (otpGeneratedTime == null) {
            return true;  // OTP hasn't been generated yet
        }
        return LocalDateTime.now().isAfter(otpGeneratedTime.plusMinutes(OTP_EXPIRY_TIME_MINUTES));  // Expiry check
    }

    // Method to verify OTP
    public static boolean verifyOtp(String enteredOtp) {
        if (isOtpExpired()) {
            System.out.println("OTP has expired.");
            return false;
        }
        return otp != null && otp.equals(enteredOtp);
    }


}
