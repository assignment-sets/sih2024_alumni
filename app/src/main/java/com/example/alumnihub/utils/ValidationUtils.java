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

    // Check if the event title is valid
    public static boolean isValidTitle(String title) {
        return title != null && !title.trim().isEmpty();
    }

    public static boolean isValidLocation(String location){
       return location != null && !location.trim().isEmpty();
    }

    public static boolean isValidRequiredSkill(String requiredSkill) {
        return requiredSkill != null && !requiredSkill.trim().isEmpty();
    }
    // Check if the event description is valid
    public static boolean isValidDescription(String description) {
        return description != null && !description.trim().isEmpty();
    }

    // Check if the event mode is valid
    public static boolean isValidMode(String mode) {
        return mode != null && !mode.trim().isEmpty() && (mode.equals("Online") || mode.equals("Offline"));
    }

    // Check if the event platform is valid
    public static boolean isValidPlatform(String platform) {
        return platform != null && !platform.trim().isEmpty();
    }

    // Check if the event venue is valid
    public static boolean isValidVenue(String venue) {
        return venue != null && !venue.trim().isEmpty();
    }

    // Check if the caption is valid
    public static boolean isValidCaption(String caption) {
        return caption != null && !caption.trim().isEmpty();
    }
    // Validate if the salary is a valid number and not empty
    public static boolean isValidSalary(String salary) {
        if (salary == null || salary.trim().isEmpty()) {
            return false;
        }
        try {
            double parsedSalary = Double.parseDouble(salary);
            return parsedSalary > 0; // Salary should be a positive number
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Validate if job mode is not null and not empty
    public static boolean isValidJobMode(String jobMode) {
        return jobMode != null && !jobMode.trim().isEmpty();
    }

    // Validate if contact email is valid using Android's pattern matcher
    public static boolean isValidContactEmail(String contactEmail) {
        return contactEmail != null && android.util.Patterns.EMAIL_ADDRESS.matcher(contactEmail).matches();
    }

    // Validate if phone number is valid (10-digit or more) and not empty
    public static boolean isValidPhoneNumber(String contactPhoneNo) {
        return contactPhoneNo != null && contactPhoneNo.trim().length() >= 10 && android.util.Patterns.PHONE.matcher(contactPhoneNo).matches();
    }

}