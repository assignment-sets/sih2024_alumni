package com.example.alumnihub.data_models;

import com.google.firebase.firestore.PropertyName;
import java.util.List;

public class University {
    private String address;
    private List<String> admins;
    private List<String> applications;
    private String contactNum;
    private String description;
    private List<String> events;
    private String logoUrl;
    private List<String> posts;
    private String publicMail;
    private String qrUrl;
    private String unvId;
    private String unvName;
    private String websiteUrl;

    // Default constructor
    public University() {
        // Default constructor is required for Firestore serialization/deserialization
    }

    // Constructor to initialize the fields
    public University(String address, List<String> admins, List<String> applications, String contactNum, String description, List<String> events, String logoUrl, List<String> posts, String publicMail, String qrUrl, String unvId, String unvName, String websiteUrl) {
        this.address = address;
        this.admins = admins;
        this.applications = applications;
        this.contactNum = contactNum;
        this.description = description;
        this.events = events;
        this.logoUrl = logoUrl;
        this.posts = posts;
        this.publicMail = publicMail;
        this.qrUrl = qrUrl;
        this.unvId = unvId;
        this.unvName = unvName;
        this.websiteUrl = websiteUrl;
    }

    @PropertyName("address")
    public String getAddress() {
        return address;
    }

    @PropertyName("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @PropertyName("admins")
    public List<String> getAdmins() {
        return admins;
    }

    @PropertyName("admins")
    public void setAdmins(List<String> admins) {
        this.admins = admins;
    }

    @PropertyName("applications")
    public List<String> getApplications() {
        return applications;
    }

    @PropertyName("applications")
    public void setApplications(List<String> applications) {
        this.applications = applications;
    }

    @PropertyName("contact_num")
    public String getContactNum() {
        return contactNum;
    }

    @PropertyName("contact_num")
    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    @PropertyName("description")
    public String getDescription() {
        return description;
    }

    @PropertyName("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @PropertyName("events")
    public List<String> getEvents() {
        return events;
    }

    @PropertyName("events")
    public void setEvents(List<String> events) {
        this.events = events;
    }

    @PropertyName("logo_url")
    public String getLogoUrl() {
        return logoUrl;
    }

    @PropertyName("logo_url")
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @PropertyName("posts")
    public List<String> getPosts() {
        return posts;
    }

    @PropertyName("posts")
    public void setPosts(List<String> posts) {
        this.posts = posts;
    }

    @PropertyName("public_mail")
    public String getPublicMail() {
        return publicMail;
    }

    @PropertyName("public_mail")
    public void setPublicMail(String publicMail) {
        this.publicMail = publicMail;
    }

    @PropertyName("qr_url")
    public String getQrUrl() {
        return qrUrl;
    }

    @PropertyName("qr_url")
    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    @PropertyName("unv_id")
    public String getUnvId() {
        return unvId;
    }

    @PropertyName("unv_id")
    public void setUnvId(String unvId) {
        this.unvId = unvId;
    }

    @PropertyName("unv_name")
    public String getUnvName() {
        return unvName;
    }

    @PropertyName("unv_name")
    public void setUnvName(String unvName) {
        this.unvName = unvName;
    }

    @PropertyName("website_url")
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    @PropertyName("website_url")
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }
}