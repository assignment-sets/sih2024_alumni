package com.example.alumnihub.data_models;

import com.google.firebase.firestore.PropertyName;

import java.util.Date;

public class Chat {
    private String chat_message_id;
    private String user_id;
    private String chat_message_text;
    private Date createdAt;
    private String pfp_pic_url;
    private String user_name;

    public Chat() {
    }

    public Chat(String chat_message_id, String user_id, String chat_message_text) {
        this.chat_message_id = chat_message_id;
        this.user_id = user_id;
        this.chat_message_text = chat_message_text;
        this.createdAt = new Date();
    }
    @PropertyName("msg_id")
    public String getChat_message_id() {
        return chat_message_id;
    }
    @PropertyName("msg_id")
    public void setChat_message_id(String chat_message_id) {
        this.chat_message_id = chat_message_id;
    }
    @PropertyName("pfp_pic_url")
    public String getPfp_pic_url() {
        return pfp_pic_url;
    }
    @PropertyName("pfp_pic_url")
    public void setPfp_pic_url(String pfp_pic_url) {
        this.pfp_pic_url = pfp_pic_url;
    }
    @PropertyName("user_name")
    public String getUser_name() {
        return user_name;
    }
    @PropertyName("user_name")
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @PropertyName("user_id")
    public String getUser_id() {
        return user_id;
    }
    @PropertyName("user_id")
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    @PropertyName("msg")
    public String getChat_message_text() {
        return chat_message_text;
    }
    @PropertyName("msg")
    public void setChat_message_text(String chat_message_text) {
        this.chat_message_text = chat_message_text;
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
