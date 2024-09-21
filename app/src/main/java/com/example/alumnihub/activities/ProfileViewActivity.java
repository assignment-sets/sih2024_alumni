package com.example.alumnihub.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.alumnihub.R;
import com.example.alumnihub.backend_services.firebase_storage.StorageServices;
import com.example.alumnihub.backend_services.firestore_db.UserServicesDB;
import com.example.alumnihub.data_models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileViewActivity extends AppCompatActivity {

    private UserServicesDB userServicesDB;
    private Button editUserInfoBtn;
    private TextView userAccountName, userFullName, userEnrollmentNo, userEmail, userType, userGradYear;
    private CircleImageView circleImageView;
    private StorageServices storageServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_view);
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(ProfileViewActivity.this, R.color.profileTabBarColor));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userAccountName = findViewById(R.id.user_account_name);
        userFullName = findViewById(R.id.user_full_name);
        userEnrollmentNo = findViewById(R.id.user_enrollment_no);
        userEmail = findViewById(R.id.user_email);
        userType = findViewById(R.id.user_type);
        userGradYear = findViewById(R.id.user_graduation_year);
        circleImageView = findViewById(R.id.profile_image);
        editUserInfoBtn = findViewById(R.id.edit_user_data);

        userServicesDB = new UserServicesDB();
        storageServices = new StorageServices();

        if (getIntent().hasExtra("selectedUser")) {
            User user = (User) getIntent().getSerializableExtra("selectedUser");
            displayUserDetails(user); // jodi user ta search fragment thaka , asa tahola oie user er data dakhabo
        } else {
            fetchCurrentUser(); // jodi ProfileActivity ta click kora user er data dakhi tahola currently LoggedIn user dakhabo
        }
    }

    private void displayUserDetails(User user) {
        userAccountName.setText(user.getUserName());
        userFullName.setText(user.getFullName());
        userEnrollmentNo.setText(user.getEnrollmentNum());
        userEmail.setText(user.getEmail());
        userType.setText(user.getType());
        userGradYear.setText(user.getGraduationYear());

        storageServices.getDownloadUrl("/profile_pics/"+user.getUserId()).addOnCompleteListener(
                new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful() && task.getResult() != null){
                            Uri userProfilePictureUri = task.getResult();

                            Picasso.get().load(userProfilePictureUri.toString())
                                    .placeholder(R.drawable.ic_default_user_profile_pic) // default background set
                                    .into(circleImageView);
                        }else {
                            showToast("Failed to fetch user profile picture");
                        }
                    }
                }
        );
    }

    private void fetchCurrentUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            String currentUserId = auth.getCurrentUser().getUid();
            userServicesDB.getUser(currentUserId).addOnCompleteListener(new OnCompleteListener<User>() {
                @Override
                public void onComplete(@NonNull Task<User> task) {
                    if (task.isSuccessful()) {
                        User user = task.getResult();
                        if (user != null) {
                            displayUserDetails(user);
                        } else {
                            showToast("No user data available");
                        }
                    } else {
                        showToast("Failed to fetch user data");
                    }
                }
            });
        } else {
            showToast("User is not logged in");
        }
    }

    // Utility method to show toast messages
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
