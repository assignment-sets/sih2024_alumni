package com.example.alumnihub.data_models;

import com.google.firebase.firestore.PropertyName;
import java.util.Date;

public class Post {
    private Date createdAt;  // Use Date to represent timestamps
    private String postId;
    private String postType;
    private String userId;

    // Default constructor
    public Post() {
        // Default constructor is required for Firestore serialization/deserialization
    }

    // Constructor to initialize the fields with current timestamp for createdAt
    public Post(String postId, String postType, String userId) {
        this.createdAt = new Date();  // Set createdAt to current timestamp
        this.postId = postId;
        this.postType = postType;
        this.userId = userId;
    }

    @PropertyName("created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    @PropertyName("created_at")
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @PropertyName("post_id")
    public String getPostId() {
        return postId;
    }

    @PropertyName("post_id")
    public void setPostId(String postId) {
        this.postId = postId;
    }

    @PropertyName("post_type")
    public String getPostType() {
        return postType;
    }

    @PropertyName("post_type")
    public void setPostType(String postType) {
        this.postType = postType;
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