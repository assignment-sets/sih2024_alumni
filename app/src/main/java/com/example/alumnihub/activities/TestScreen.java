package com.example.alumnihub.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.alumnihub.R;
import com.example.alumnihub.backend_services.firestore_db.SocialPostServicesDB;
import com.example.alumnihub.data_models.SocialPost;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class TestScreen extends AppCompatActivity {

    private SocialPostServicesDB socialPostServicesDB;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resultTextView = findViewById(R.id.sampleTextView);
        socialPostServicesDB = new SocialPostServicesDB();

        // Test the methods
//        testAddSocialPost();
//        testDeleteSocialPostById("exampleSocialPostId");
//        testGetSocialPostById("103");
//        testGetAllSocialPosts();
    }

    private void testAddSocialPost() {
        SocialPost newSocialPost = new SocialPost("Example Caption", "http://example.com/image.jpg", "examplePostId", "exampleSocialPostId");
        socialPostServicesDB.addSocialPost(newSocialPost).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    resultTextView.setText("Social post added successfully");
                } else {
                    resultTextView.setText("Error: " + task.getException().getMessage());
                }
            }
        });
    }

    private void testDeleteSocialPostById(String socialPostId) {
        socialPostServicesDB.deleteSocialPostById(socialPostId).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    resultTextView.setText("Social post deleted successfully");
                } else {
                    resultTextView.setText("Error: " + task.getException().getMessage());
                }
            }
        });
    }

    private void testGetSocialPostById(String socialPostId) {
        socialPostServicesDB.getSocialPostById(socialPostId).addOnCompleteListener(new OnCompleteListener<SocialPost>() {
            @Override
            public void onComplete(Task<SocialPost> task) {
                if (task.isSuccessful()) {
                    SocialPost socialPost = task.getResult();
                    if (socialPost != null) {
                        resultTextView.setText("Social Post Caption: " + socialPost.getCaption());
                    } else {
                        resultTextView.setText("Social post not found");
                    }
                } else {
                    resultTextView.setText("Error: " + task.getException().getMessage());
                }
            }
        });
    }

    private void testGetAllSocialPosts() {
        socialPostServicesDB.getAllSocialPosts().addOnCompleteListener(new OnCompleteListener<List<SocialPost>>() {
            @Override
            public void onComplete(Task<List<SocialPost>> task) {
                if (task.isSuccessful()) {
                    List<SocialPost> socialPosts = task.getResult();
                    if (socialPosts != null && !socialPosts.isEmpty()) {
                        StringBuilder result = new StringBuilder("All Social Posts:\n");
                        for (SocialPost socialPost : socialPosts) {
                            result.append(socialPost.getCaption()).append("\n");
                        }
                        resultTextView.setText(result.toString());
                    } else {
                        resultTextView.setText("No social posts found");
                    }
                } else {
                    resultTextView.setText("Error: " + task.getException().getMessage());
                }
            }
        });
    }
}