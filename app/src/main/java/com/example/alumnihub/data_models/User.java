package com.example.alumnihub.data_models;

import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String bio;
    private String contactNum;
    private String currentLocation;
    private String domain;
    private String email;
    private String enrollmentNum;
    private String fullName;
    private String graduationYear;
    private String idProofUrl;
    private boolean isComplete;
    private boolean isVerified;
    private String pfPicUrl;
    private List<String> posts;
    private String type;
    private String userId;
    private String userName;
    private String workplace;
    private String yearOfStudy;

    // Default constructor initializes all variables to null/false
    public User() {
        this.bio = null;
        this.contactNum = null;
        this.currentLocation = null;
        this.domain = null;
        this.email = null;
        this.enrollmentNum = null;
        this.fullName = null;
        this.graduationYear = null;
        this.idProofUrl = null;
        this.isComplete = false;
        this.isVerified = false;
        this.pfPicUrl = null;
        this.posts = new ArrayList<>();
        this.type = null;
        this.userId = null;
        this.userName = null;
        this.workplace = null;
        this.yearOfStudy = null;
    }

    @PropertyName("bio")
    public String getBio() {
        return bio;
    }

    @PropertyName("bio")
    public void setBio(String bio) {
        this.bio = bio;
    }

    @PropertyName("contact_num")
    public String getContactNum() {
        return contactNum;
    }

    @PropertyName("contact_num")
    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    @PropertyName("current_location")
    public String getCurrentLocation() {
        return currentLocation;
    }

    @PropertyName("current_location")
    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    @PropertyName("domain")
    public String getDomain() {
        return domain;
    }

    @PropertyName("domain")
    public void setDomain(String domain) {
        this.domain = domain;
    }

    @PropertyName("email")
    public String getEmail() {
        return email;
    }

    @PropertyName("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @PropertyName("enrollment_num")
    public String getEnrollmentNum() {
        return enrollmentNum;
    }

    @PropertyName("enrollment_num")
    public void setEnrollmentNum(String enrollmentNum) {
        this.enrollmentNum = enrollmentNum;
    }

    @PropertyName("full_name")
    public String getFullName() {
        return fullName;
    }

    @PropertyName("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @PropertyName("graduation_year")
    public String getGraduationYear() {
        return graduationYear;
    }

    @PropertyName("graduation_year")
    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    @PropertyName("id_proof_url")
    public String getIdProofUrl() {
        return idProofUrl;
    }

    @PropertyName("id_proof_url")
    public void setIdProofUrl(String idProofUrl) {
        this.idProofUrl = idProofUrl;
    }

    @PropertyName("is_complete")
    public boolean isComplete() {
        return isComplete;
    }

    @PropertyName("is_complete")
    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    @PropertyName("is_verified")
    public boolean isVerified() {
        return isVerified;
    }

    @PropertyName("is_verified")
    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    @PropertyName("pf_pic_url")
    public String getPfPicUrl() {
        return pfPicUrl;
    }

    @PropertyName("pf_pic_url")
    public void setPfPicUrl(String pfPicUrl) {
        this.pfPicUrl = pfPicUrl;
    }

    @PropertyName("posts")
    public List<String> getPosts() {
        return posts;
    }

    @PropertyName("posts")
    public void setPosts(List<String> posts) {
        this.posts = posts;
    }

    @PropertyName("type")
    public String getType() {
        return type;
    }

    @PropertyName("type")
    public void setType(String type) {
        this.type = type;
    }

    @PropertyName("user_id")
    public String getUserId() {
        return userId;
    }

    @PropertyName("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @PropertyName("user_name")
    public String getUserName() {
        return userName;
    }

    @PropertyName("user_name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @PropertyName("workplace")
    public String getWorkplace() {
        return workplace;
    }

    @PropertyName("workplace")
    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    @PropertyName("year_of_study")
    public String getYearOfStudy() {
        return yearOfStudy;
    }

    @PropertyName("year_of_study")
    public void setYearOfStudy(String yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }
}

