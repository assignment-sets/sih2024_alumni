package com.example.alumnihub.data_models;

import com.google.firebase.firestore.PropertyName;

public class JobPost {
    private String contact_email;
    private String contact_num;
    private String description;
    private String domain;
    private String job_id;
    private String job_location;
    private String job_title;
    private String post_id;
    private String req_skills;
    private String salary;
    private String user_id;
    private String work_mode;

    public JobPost() {
    }

    public JobPost(String contact_email, String contact_num, String description, String domain, String job_id, String job_location, String job_title, String post_id, String req_skills, String salary, String user_id, String work_mode) {
        this.contact_email = contact_email;
        this.contact_num = contact_num;
        this.description = description;
        this.domain = domain;
        this.job_id = job_id;
        this.job_location = job_location;
        this.job_title = job_title;
        this.post_id = post_id;
        this.req_skills = req_skills;
        this.salary = salary;
        this.user_id = user_id;
        this.work_mode = work_mode;
    }

    @PropertyName("contact_email")
    public String getContact_email() {
        return contact_email;
    }

    @PropertyName("contact_email")
    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    @PropertyName("contact_num")
    public String getContact_num() {
        return contact_num;
    }

    @PropertyName("contact_num")
    public void setContact_num(String contact_num) {
        this.contact_num = contact_num;
    }

    @PropertyName("description")
    public String getDescription() {
        return description;
    }

    @PropertyName("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @PropertyName("domain")
    public String getDomain() {
        return domain;
    }

    @PropertyName("domain")
    public void setDomain(String domain) {
        this.domain = domain;
    }

    @PropertyName("job_id")
    public String getJob_id() {
        return job_id;
    }

    @PropertyName("job_id")
    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    @PropertyName("job_location")
    public String getJob_location() {
        return job_location;
    }

    @PropertyName("job_location")
    public void setJob_location(String job_location) {
        this.job_location = job_location;
    }

    @PropertyName("job_title")
    public String getJob_title() {
        return job_title;
    }

    @PropertyName("job_title")
    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    @PropertyName("post_id")
    public String getPost_id() {
        return post_id;
    }

    @PropertyName("post_id")
    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    @PropertyName("req_skills")
    public String getReq_skills() {
        return req_skills;
    }

    @PropertyName("req_skills")
    public void setReq_skills(String req_skills) {
        this.req_skills = req_skills;
    }

    @PropertyName("salary")
    public String getSalary() {
        return salary;
    }

    @PropertyName("salary")
    public void setSalary(String salary) {
        this.salary = salary;
    }

    @PropertyName("user_id")
    public String getUser_id() {
        return user_id;
    }

    @PropertyName("user_id")
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @PropertyName("work_mode")
    public String getWork_mode() {
        return work_mode;
    }

    @PropertyName("work_mode")
    public void setWork_mode(String work_mode) {
        this.work_mode = work_mode;
    }
}
