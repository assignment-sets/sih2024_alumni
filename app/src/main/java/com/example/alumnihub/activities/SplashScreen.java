// Java
package com.example.alumnihub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alumnihub.R;
import com.example.alumnihub.backend_services.firebase_auth.AuthServices;
import com.example.alumnihub.backend_services.firestore_db.UserServicesDB;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    private Button getStartedBtn;
    private TextView alumniHub, descriptionText;
    private ImageView girlPic, topArc;
    private AuthServices authService;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        initializeViews();
        authService = new AuthServices();
        currentUser = authService.getCurrentUser();

        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleGetStartedClick();
            }
        });
    }

    private void initializeViews() {
        getStartedBtn = findViewById(R.id.getStartedButton);
        alumniHub = findViewById(R.id.alumniHubTextView);
        descriptionText = findViewById(R.id.descriptionText);
        girlPic = findViewById(R.id.girlImgView);
        topArc = findViewById(R.id.topArcImg);
    }

    private void handleGetStartedClick() {
        if (currentUser == null) {
            navigateToLoginScreen();
        } else {
            checkUserProfile();
        }
    }

    private void navigateToLoginScreen() {
        startActivity(new Intent(SplashScreen.this, LoginScreen.class));
        finish();
    }

    private void checkUserProfile() {
        UserServicesDB userServicesDB = new UserServicesDB();
        userServicesDB.getUser(currentUser.getUid())
                .addOnSuccessListener(user -> {
                    if (user != null) {
                        if (!user.isComplete()) {
                            navigateToAdditionalDetailsFormScreen();
                        } else {
                            navigateToMainActivity();
                        }
                    } else {
                        navigateToLoginScreen();
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    navigateToLoginScreen();
                });
    }

    private void navigateToAdditionalDetailsFormScreen() {
        startActivity(new Intent(SplashScreen.this, AdditionalDetailsFormScreen.class));
        finish();
    }

    private void navigateToMainActivity() {
        startActivity(new Intent(SplashScreen.this, MainActivity.class));
        finish();
    }
}