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

public class SplashScreen extends AppCompatActivity {
    private Button getStartedBtn;
    private TextView alumniHub, descriptionText;
    private ImageView girlPic, topArc;
    private AuthServices authService;

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

        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authService.getCurrentUser() == null) {
                    startActivity(new Intent(SplashScreen.this, LoginScreen.class));
                } else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
                finish();
            }
        });
    }
}