package com.hmsApp;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class AdminPasswordCreate {

    public static void main(String [] args)
    {
        String encodedPw = BCrypt.hashpw("admin123", BCrypt.gensalt(10));
        System.out.println(encodedPw);


    }
}
