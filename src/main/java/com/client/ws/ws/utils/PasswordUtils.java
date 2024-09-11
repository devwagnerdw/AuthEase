package com.client.ws.ws.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

    private PasswordUtils(){}

    public static String encode(String password){
        return new BCryptPasswordEncoder().encode(password);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, encodedPassword);
    }

}