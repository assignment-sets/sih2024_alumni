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
    }

    // Check if the username is valid
    public static boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty();
    }

    // Check if the bio is valid
    public static boolean isValidBio(String bio) {
        return bio != null && !bio.trim().isEmpty();
    }

    // Check if the contact number is valid
    public static boolean isValidContactNum(String contactNum) {
        return contactNum != null && contactNum.matches("\\d{10}");
    }


    // Check if the domain is valid
    public static boolean isValidDomain(String domain) {
        return domain != null && !domain.trim().isEmpty();
    }

    // Check if the enrollment number is valid
    public static boolean isValidEnrollmentNum(String enrollmentNum) {
        return enrollmentNum != null && !enrollmentNum.trim().isEmpty();
    }

    // Check if the full name is valid
    public static boolean isValidFullName(String fullName) {
        return fullName != null && !fullName.trim().isEmpty();
    }

    // Check if the graduation year is valid
    public static boolean isValidGraduationYear(String graduationYear) {
        return graduationYear != null && graduationYear.matches("\\d{4}");
    }

    // Check if the ID proof URL is valid
    public static boolean isValidIdProofUrl(String idProofUrl) {
        return idProofUrl != null && Patterns.WEB_URL.matcher(idProofUrl).matches();
    }

    // Check if the profile picture URL is valid
    public static boolean isValidPfPicUrl(String pfPicUrl) {
        return pfPicUrl != null && Patterns.WEB_URL.matcher(pfPicUrl).matches();
    }

    // Check if the workplace is valid
    public static boolean isValidWorkplace(String workplace) {
        return workplace != null && !workplace.trim().isEmpty();
    }

    // Check if the year of study is valid
    public static boolean isValidYearOfStudy(String yearOfStudy) {
        return yearOfStudy != null && yearOfStudy.matches("Year \\d");
    }
}