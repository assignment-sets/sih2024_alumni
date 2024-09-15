package com.example.alumnihub.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.alumnihub.R;
import com.example.alumnihub.backend_services.firebase_auth.AuthServices;
import com.example.alumnihub.backend_services.firebase_storage.StorageServices;
import com.example.alumnihub.backend_services.firestore_db.EventServicesDB;
import com.example.alumnihub.backend_services.firestore_db.PostServicesDB;
import com.example.alumnihub.backend_services.firestore_db.SocialPostServicesDB;
import com.example.alumnihub.backend_services.firestore_db.UserServicesDB;
import com.example.alumnihub.data_models.Event;
import com.example.alumnihub.data_models.Post;
import com.example.alumnihub.data_models.SocialPost;
import com.example.alumnihub.utils.ImagePickerUtil;
import com.example.alumnihub.utils.ValidationUtils;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class CreateSocialPostFormScreen extends AppCompatActivity {

    private EditText captionEditText;
    private TextView imgUrlLabel;
    private ImageView uploadedImageView;
    private ImageButton uploadImageButton;
    private Button submitButton;
    private Uri ImgUri = null;
    private String ImgUrl = null;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_social_post_form_screen);

        // Change the status bar color
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bgSplashScreen));
        }

        View rootView = findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemInsets.left, systemInsets.top, systemInsets.right, systemInsets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        AuthServices authService = new AuthServices();
        currentUser = authService.getCurrentUser();

        captionEditText = findViewById(R.id.caption);
        imgUrlLabel = findViewById(R.id.image_url_label);
        uploadedImageView = findViewById(R.id.uploaded_image);
        uploadImageButton = findViewById(R.id.upload_image_btn);
        submitButton = findViewById(R.id.submit_btn);

        // Set click listener for upload avatar button
        setUploadAvatarButtonListener();

        // Set click listener for submit button
        submitButton.setOnClickListener(v -> handleSubmit());
    }

    private void setUploadAvatarButtonListener() {
    ActivityResultLauncher<Intent> avatarLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    ImgUri = ImagePickerUtil.getUploadedImageUri(result.getData());
                    ImagePickerUtil.loadImageIntoView(this, ImgUri, uploadedImageView); // Use the loadImageIntoView method
                    String fileName = ImagePickerUtil.getFileName(this, ImgUri);
                    imgUrlLabel.setText(fileName); // Set the file name to the avatar URL label
                }
            });

    uploadImageButton.setOnClickListener(v -> ImagePickerUtil.requestImageFromGallery(this, avatarLauncher));
}

    private void handleSubmit() {
        String captionValue = captionEditText.getText().toString().trim();

        if (!ValidationUtils.isValidCaption(captionValue)) {
            showToast("Invalid title.");
            return;
        }

        PostServicesDB postServicesDB = new PostServicesDB();
        SocialPostServicesDB socialPostServicesDB = new SocialPostServicesDB();
        UserServicesDB userServicesDB = new UserServicesDB();
        String postId = postServicesDB.generateUniquePostId();
        String socialPostId = socialPostServicesDB.generateUniqueSocialPostId();

        StorageServices storageServices = new StorageServices();
        String imgPath = "/posts/socials/" + socialPostId;

        // Upload avatar if available
        Task<Uri> avatarUploadTask = Tasks.forResult(null);
        if (ImgUri != null) {
            avatarUploadTask = storageServices.uploadFile(ImgUri, imgPath)
                    .continueWithTask(task -> {
                        if (task.isSuccessful()) {
                            return storageServices.getDownloadUrl(imgPath);
                        } else {
                            throw task.getException();
                        }
                    });
        }

        // Wait for avatar upload to complete
        avatarUploadTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ImgUrl = task.getResult() != null ? task.getResult().toString() : null;

                // Create post object
                Post post = new Post(postId, "social", currentUser.getUid());

                // Add post to Firestore
                postServicesDB.addPost(post)
                        .addOnSuccessListener(aVoid -> {
                            // Create event object
                            SocialPost socialPost = new SocialPost(captionValue, ImgUrl, postId, socialPostId, currentUser.getUid());

                            // Add event to Firestore
                            socialPostServicesDB.addSocialPost(socialPost)
                                    .addOnSuccessListener(aVoid1 -> {
                                        // Fetch current user
                                        userServicesDB.getUser(currentUser.getUid())
                                                .addOnSuccessListener(user -> {
                                                    if (user != null) {
                                                        // Add new post ID to user's posts
                                                        List<String> userPosts = user.getPosts();
                                                        if (userPosts == null) {
                                                            userPosts = new ArrayList<>();
                                                        }
                                                        userPosts.add(postId);
                                                        user.setPosts(userPosts);

                                                        // Update user in Firestore
                                                        userServicesDB.updateUser(currentUser.getUid(), user)
                                                                .addOnSuccessListener(aVoid2 -> {
                                                                    showToast("Event created successfully.");
                                                                    finish();
                                                                })
                                                                .addOnFailureListener(e -> {
                                                                    Log.e("UserUpdateError", "Error updating user", e);
                                                                    showToast("Error updating user.");
                                                                });
                                                    } else {
                                                        Log.e("UserFetchError", "User not found.");
                                                        showToast("User not found.");
                                                    }
                                                })
                                                .addOnFailureListener(e -> {
                                                    Log.e("UserFetchError", "Error fetching user", e);
                                                    showToast("Error fetching user.");
                                                });
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("EventCreationError", "Error creating event", e);
                                        showToast("Error creating event.");
                                    });
                        })
                        .addOnFailureListener(e -> {
                            Log.e("PostCreationError", "Error creating post", e);
                            showToast("Error creating post.");
                        });
            } else {
                Log.e("Storage", "Failed to upload avatar", task.getException());
                showToast("Failed to upload avatar.");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(CreateSocialPostFormScreen.this, message, Toast.LENGTH_SHORT).show();
    }
}