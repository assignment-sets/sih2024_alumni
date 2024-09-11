package com.example.alumnihub.data_models;

import com.google.firebase.firestore.PropertyName;

public class Application {
    private String applicationId;
    private String enrollmentNo;
    private String idProofUrl;
    private boolean isAccepted;
    private String userId;

    // Default constructor
    public Application() {
        // Default constructor is required for Firestore serialization/deserialization
    }

    // Constructor to initialize the fields
    public Application(String applicationId, String enrollmentNo, String idProofUrl, boolean isAccepted, String userId) {
        this.applicationId = applicationId;
        this.enrollmentNo = enrollmentNo;
        this.idProofUrl = idProofUrl;
        this.isAccepted = isAccepted;
        this.userId = userId;
    }

    @PropertyName("application_id")
    public String getApplicationId() {
        return applicationId;
    }

    @PropertyName("application_id")
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    @PropertyName("enrollment_no")
    public String getEnrollmentNo() {
        return enrollmentNo;
    }

    @PropertyName("enrollment_no")
    public void setEnrollmentNo(String enrollmentNo) {
        this.enrollmentNo = enrollmentNo;
    }

    @PropertyName("id_proof_url")
    public String getIdProofUrl() {
        return idProofUrl;
    }

    @PropertyName("id_proof_url")
    public void setIdProofUrl(String idProofUrl) {
        this.idProofUrl = idProofUrl;
    }

    @PropertyName("is_accepted")
    public boolean isAccepted() {
        return isAccepted;
    }

    @PropertyName("is_accepted")
    public void setAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    @PropertyName("user_id")
    public String getUserId() {
        return userId;
    }

    @PropertyName("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
