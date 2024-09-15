package com.example.alumnihub.data_models;

import com.google.firebase.firestore.PropertyName;

public class SocialPost {
    private String caption;
    private String imageUrl;
    private String postId;
    private String socialPostId;
    private String userId; // New field

    // Default constructor
    public SocialPost() {
        // Default constructor is required for Firestore serialization/deserialization
    }

    // Constructor to initialize the fields
    public SocialPost(String caption, String imageUrl, String postId, String socialPostId, String userId) {
        this.caption = caption;
        this.imageUrl = imageUrl;
        this.postId = postId;
        this.socialPostId = socialPostId;
        this.userId = userId; // Initialize new field
    }

    @PropertyName("caption")
    public String getCaption() {
        return caption;
    }

    @PropertyName("caption")
    public void setCaption(String caption) {
        this.caption = caption;
    }

    @PropertyName("image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    @PropertyName("image_url")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @PropertyName("post_id")
    public String getPostId() {
        return postId;
    }

    @PropertyName("post_id")
    public void setPostId(String postId) {
        this.postId = postId;
    }

    @PropertyName("social_post_id")
    public String getSocialPostId() {
        return socialPostId;
    }

    @PropertyName("social_post_id")
    public void setSocialPostId(String socialPostId) {
        this.socialPostId = socialPostId;
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