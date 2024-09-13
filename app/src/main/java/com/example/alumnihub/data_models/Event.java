package com.example.alumnihub.data_models;

import com.google.firebase.firestore.PropertyName;

public class Event {
    private String avatarUrl;
    private String description;
    private String eventId;
    private String mode;
    private String platform;
    private String postId;
    private String title;
    private String venue;

    // Default constructor
    public Event() {
        // Default constructor is required for Firestore serialization/deserialization
    }

    // Constructor to initialize the fields
    public Event(String avatarUrl, String description, String eventId, String mode, String platform, String postId, String title, String venue) {
        this.avatarUrl = avatarUrl;
        this.description = description;
        this.eventId = eventId;
        this.mode = mode;
        this.platform = platform;
        this.postId = postId;
        this.title = title;
        this.venue = venue;
    }

    @PropertyName("avatar_url")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @PropertyName("avatar_url")
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @PropertyName("description")
    public String getDescription() {
        return description;
    }

    @PropertyName("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @PropertyName("event_id")
    public String getEventId() {
        return eventId;
    }

    @PropertyName("event_id")
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @PropertyName("mode")
    public String getMode() {
        return mode;
    }

    @PropertyName("mode")
    public void setMode(String mode) {
        this.mode = mode;
    }

    @PropertyName("platform")
    public String getPlatform() {
        return platform;
    }

    @PropertyName("platform")
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @PropertyName("post_id")
    public String getPostId() {
        return postId;
    }

    @PropertyName("post_id")
    public void setPostId(String postId) {
        this.postId = postId;
    }

    @PropertyName("title")
    public String getTitle() {
        return title;
    }

    @PropertyName("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @PropertyName("venue")
    public String getVenue() {
        return venue;
    }

    @PropertyName("venue")
    public void setVenue(String venue) {
        this.venue = venue;
    }
}