package com.example.alumnihub.data_models;

import com.google.firebase.firestore.PropertyName;
import java.util.Date;

public class UserName {
    private Date createdAt;  // Field to store the timestamp as a String

    // Default constructor
    public UserName() {
        // Initialize with the current time in the specified format
        this.createdAt = new Date();
    }

    @PropertyName("created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    @PropertyName("created_at")
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}