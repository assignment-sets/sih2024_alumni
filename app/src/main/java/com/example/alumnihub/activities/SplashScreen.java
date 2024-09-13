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

        getStartedBtn = findViewById(R.id.getStartedButton);
        alumniHub = findViewById(R.id.alumniHubTextView);
        descriptionText = findViewById(R.id.descriptionText);
        girlPic = findViewById(R.id.girlImgView);
        topArc = findViewById(R.id.topArcImg);
        authService = new AuthServices();
        currentUser = authService.getCurrentUser();

        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    // No user logged in, redirect to LoginScreen
                    startActivity(new Intent(SplashScreen.this, LoginScreen.class));
                    finish();  // Ensure the SplashScreen is finished so the user can't go back to it
                } else {
                    // User is logged in, check if their profile is complete
                    UserServicesDB userServicesDB = new UserServicesDB();
                    userServicesDB.getUser(currentUser.getUid())
                            .addOnSuccessListener(user -> {
                                if (user != null) {
                                    if (!user.isComplete()) {
                                        // Profile is incomplete, redirect to AdditionalDetailsFormScreen
                                        startActivity(new Intent(SplashScreen.this, AdditionalDetailsFormScreen.class));
                                    } else {
                                        // Profile is complete, redirect to MainActivity
                                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                                    }
                                    finish();  // Ensure the SplashScreen is finished
                                } else {
                                    // User document not found, handle accordingly
                                    startActivity(new Intent(SplashScreen.this, LoginScreen.class));
                                    finish();
                                }
                            })
                            .addOnFailureListener(e -> {
                                // Handle Firestore failure
                                e.printStackTrace();  // Or use a logging library
                                startActivity(new Intent(SplashScreen.this, LoginScreen.class));
                                finish();
                            });
                }
            }
        });
    }
}
