package com.example.alumnihub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.alumnihub.R;

public class SplashScreen extends AppCompatActivity {
    private Button getStartedBtn;
    private TextView alumniHub, descriptionText;
    private ImageView girlPic, topArc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getStartedBtn = findViewById(R.id.getStartedButton);
        alumniHub = findViewById(R.id.alumniHubTextView);
        descriptionText = findViewById(R.id.descriptionText);
        girlPic = findViewById(R.id.girlImgView);
        topArc = findViewById(R.id.topArcImg);

        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreen.this, LoginScreen.class));
                finish();
            }
        });
    }
}