package com.example.alumnihub.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.alumnihub.R;
import com.example.alumnihub.backend_services.firebase_auth.AuthServices;
import com.example.alumnihub.backend_services.firestore_db.JobPostServicesDB;
import com.example.alumnihub.backend_services.firestore_db.PostServicesDB;
import com.example.alumnihub.backend_services.firestore_db.UserServicesDB;
import com.example.alumnihub.data_models.JobPost;
import com.example.alumnihub.data_models.Post;
import com.example.alumnihub.data_models.User;
import com.example.alumnihub.utils.ValidationUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class JobPostFormActivity extends AppCompatActivity {
    private TextView postJob;
    private EditText jobTitle, jobLocation, jobDescription, jobDomain, jobRequiredSkill, jobSalary, jobMode, jobContactEmail, jobContactPhoneNo;
    private Button postJobButton;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_job_post_form);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AuthServices authService = new AuthServices();
        firebaseUser = authService.getCurrentUser();

        postJob = findViewById(R.id.jobFormTextView);
        jobTitle = findViewById(R.id.jobTtileField);
        jobLocation = findViewById(R.id.jobLocationField);
        jobDescription = findViewById(R.id.jobDescriptionField);
        jobDomain = findViewById(R.id.jobDomainField);
        jobRequiredSkill = findViewById(R.id.jobRequiredSkillField);
        jobSalary = findViewById(R.id.jobSalaryField);
        jobMode = findViewById(R.id.jobWorkModeField);
        jobContactEmail = findViewById(R.id.contactEmailField);
        jobContactPhoneNo = findViewById(R.id.contactNumberField);

        postJobButton = findViewById(R.id.job_post_submit_btn);

        postJobButton.setOnClickListener(v -> postJobFormData());
    }

    private void postJobFormData() {
        String title = jobTitle.getText().toString().trim();
        String location = jobLocation.getText().toString().trim();
        String description = jobDescription.getText().toString().trim();
        String domain = jobDomain.getText().toString().trim();
        String requiredSkills = jobRequiredSkill.getText().toString().trim();
        String salary = jobSalary.getText().toString().trim();
        String mode = jobMode.getText().toString().trim();
        String contactEmail = jobContactEmail.getText().toString().trim();
        String contactPhoneNo = jobContactPhoneNo.getText().toString().trim();

        if (!ValidationUtils.isValidTitle(title)) {
            showToast("Invalid Title");
            return;
        }
        if (!ValidationUtils.isValidLocation(location)) {
            showToast("Please enter the job location.");
            return;
        }
        if (!ValidationUtils.isValidDescription(description)) {
            showToast("Invalid Description");
            return;
        }
        if (!ValidationUtils.isValidDomain(domain)) {
            showToast("Please enter the job domain.");
            return;
        }
        if (!ValidationUtils.isValidRequiredSkill(requiredSkills)) {
            showToast("Please enter required skills");
            return;
        }
        if (!ValidationUtils.isValidSalary(salary)) {
            showToast("Please enter a valid salary");
            return;
        }
        if (!ValidationUtils.isValidJobMode(mode)) {
            showToast("Please enter a valid job mode");
            return;
        }
        if (!ValidationUtils.isValidContactEmail(contactEmail)) {
            showToast("Please enter a valid contact email");
            return;
        }
        if (!ValidationUtils.isValidPhoneNumber(contactPhoneNo)) {
            showToast("Please enter a valid contact phone number");
            return;
        }

        PostServicesDB postServicesDB = new PostServicesDB();
        JobPostServicesDB jobPostServicesDB = new JobPostServicesDB();
        UserServicesDB userServicesDB = new UserServicesDB();
        String postId = postServicesDB.generateUniquePostId();
        String jobId = jobPostServicesDB.generateUniqueJobId();

        // Creating post
        Post post = new Post(postId, "jobs", firebaseUser.getUid());

        postServicesDB.addPost(post)
                .addOnSuccessListener(unused -> {
                    JobPost jobPost = new JobPost(
                            contactEmail, contactPhoneNo, description, domain, jobId, location, title,
                            postId, requiredSkills, salary, firebaseUser.getUid(), mode
                    );

                    // Adding job post to Firestore
                    jobPostServicesDB.addJobPost(jobPost)
                            .addOnSuccessListener(aVoid -> {
                                // Fetching the user to update their post list
                                userServicesDB.getUser(firebaseUser.getUid())
                                        .addOnSuccessListener(user -> {
                                            if (user != null) {
                                                List<String> userPosts = user.getPosts();
                                                if (userPosts == null) {
                                                    userPosts = new ArrayList<>();
                                                }
                                                userPosts.add(postId);
                                                user.setPosts(userPosts);

                                                // Updating user in Firestore
                                                userServicesDB.updateUser(firebaseUser.getUid(), user)
                                                        .addOnSuccessListener(aVoid1 -> {
                                                            showToast("Job posted successfully.");
                                                            finish();
                                                        })
                                                        .addOnFailureListener(e -> {
                                                            showToast("Error updating user.");
                                                        });
                                            } else {
                                                showToast("User not found.");
                                            }
                                        })
                                        .addOnFailureListener(e -> showToast("Error fetching user."));
                            })
                            .addOnFailureListener(e -> showToast("Error creating job post."));
                })
                .addOnFailureListener(e -> showToast("Error creating post."));
    }

    private void showToast(String message) {
        Toast.makeText(JobPostFormActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
