package com.example.alumnihub.data_models;

import com.google.firebase.firestore.PropertyName;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class UserName {
    private String createdAt;  // Field to store the timestamp as a String

    // Default constructor
    public UserName() {
        // Initialize with the current time in the specified format
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy 'at' hh:mm:ss a z", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC+5:30"));
        this.createdAt = sdf.format(new Date());
    }

    @PropertyName("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @PropertyName("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}