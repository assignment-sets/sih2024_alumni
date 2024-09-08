package com.example.alumnihub.utils;

import android.util.Patterns;

public class ValidationUtils {
    // Check if the email is valid
    public static boolean isValidEmail(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Check if the password meets length or complexity criteria
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
        // You can add more rules like containing special characters or numbers
    }

    // You can also add other validation methods
    public static boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty();
        // Add any other checks like length or special characters
    }
}
