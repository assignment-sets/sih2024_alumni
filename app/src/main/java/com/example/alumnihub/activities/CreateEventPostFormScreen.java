package com.example.alumnihub.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import com.example.alumnihub.backend_services.firestore_db.EventServicesDB;
import com.example.alumnihub.backend_services.firestore_db.PostServicesDB;
import com.example.alumnihub.backend_services.firebase_storage.StorageServices;
import com.example.alumnihub.backend_services.firestore_db.UserServicesDB;
import com.example.alumnihub.data_models.Event;
import com.example.alumnihub.data_models.Post;
import com.example.alumnihub.utils.ImagePickerUtil;
import com.example.alumnihub.utils.ValidationUtils;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateEventPostFormScreen extends AppCompatActivity {

    private EditText title, description, platform, venue;
    private TextView avatarUrlLabel;
    private Spinner modeSpinner;
    private ImageButton uploadAvatarButton;
    private Button submitButton;
    private Uri avatarUri = null;
    private String avatarUrl = null;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_post_form_screen);

        // Change the status bar color
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bgSplashScreen));
        }

//          Set proper window view padding
        View rootView = findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemInsets.left, systemInsets.top, systemInsets.right, systemInsets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        AuthServices authService = new AuthServices();
        currentUser = authService.getCurrentUser();

        // Initialize views
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        modeSpinner = findViewById(R.id.mode_spinner);
        platform = findViewById(R.id.platform);
        venue = findViewById(R.id.venue);
        uploadAvatarButton = findViewById(R.id.upload_avatar_btn);
        submitButton = findViewById(R.id.submit_btn);
        avatarUrlLabel = findViewById(R.id.avatar_url_label);

        // Populate mode spinner
        populateModeSpinner();

        // Set click listener for upload avatar button
        setUploadAvatarButtonListener();

        // Set click listener for submit button
        submitButton.setOnClickListener(v -> handleSubmit());
    }

    private void populateModeSpinner() {
        List<String> modeOptions = new ArrayList<>();
        modeOptions.add("Online");
        modeOptions.add("Offline");
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, modeOptions);
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(modeAdapter);
    }

    private void setUploadAvatarButtonListener() {
        ActivityResultLauncher<Intent> avatarLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        avatarUri = ImagePickerUtil.getUploadedImageUri(result.getData());
                        String fileName = ImagePickerUtil.getFileName(this, avatarUri);
                        avatarUrlLabel.setText(fileName); // Set the file name to the avatar URL label
                    }
                });

        uploadAvatarButton.setOnClickListener(v -> ImagePickerUtil.requestImageFromGallery(this, avatarLauncher));
    }

    private void handleSubmit() {
        String titleValue = title.getText().toString().trim();
        String descriptionValue = description.getText().toString().trim();
        String modeValue = modeSpinner.getSelectedItem().toString();
        String platformValue = platform.getText().toString().trim();
        String venueValue = venue.getText().toString().trim();

        if (!ValidationUtils.isValidTitle(titleValue)) {
            showToast("Invalid title.");
            return;
        }
        if (!ValidationUtils.isValidDescription(descriptionValue)) {
            showToast("Invalid description.");
            return;
        }
        if (!ValidationUtils.isValidMode(modeValue)) {
            showToast("Invalid mode.");
            return;
        }
        if (!ValidationUtils.isValidPlatform(platformValue)) {
            showToast("Invalid platform.");
            return;
        }
        if (!ValidationUtils.isValidVenue(venueValue)) {
            showToast("Invalid venue.");
            return;
        }

        PostServicesDB postServicesDB = new PostServicesDB();
        EventServicesDB eventServicesDB = new EventServicesDB();
        UserServicesDB userServicesDB = new UserServicesDB();
        String postId = postServicesDB.generateUniquePostId();
        String eventId = eventServicesDB.generateUniqueEventId();

        StorageServices storageServices = new StorageServices();
        String avatarPath = "/posts/event_avatars/" + eventId;

        // Upload avatar if available
        Task<Uri> avatarUploadTask = Tasks.forResult(null);
        if (avatarUri != null) {
            avatarUploadTask = storageServices.uploadFile(avatarUri, avatarPath)
                    .continueWithTask(task -> {
                        if (task.isSuccessful()) {
                            return storageServices.getDownloadUrl(avatarPath);
                        } else {
                            throw task.getException();
                        }
                    });
        }

        // Wait for avatar upload to complete
        avatarUploadTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                avatarUrl = task.getResult() != null ? task.getResult().toString() : null;

                // Create post object
                Post post = new Post(postId, "event", currentUser.getUid());

                // Add post to Firestore
                postServicesDB.addPost(post)
                        .addOnSuccessListener(aVoid -> {
                            // Create event object
                            Event event = new Event(avatarUrl, descriptionValue, eventId, modeValue, platformValue, postId, titleValue, venueValue, currentUser.getUid());

                            // Add event to Firestore
                            eventServicesDB.addEvent(event)
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
        Toast.makeText(CreateEventPostFormScreen.this, message, Toast.LENGTH_SHORT).show();
    }
}