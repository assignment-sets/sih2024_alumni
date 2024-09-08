package com.example.alumnihub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.example.alumnihub.R;
import com.example.alumnihub.backend_services.firebase_auth.AuthServices;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button signOutButton;
    private TextView textView;
    private AuthServices authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        signOutButton = findViewById(R.id.signOutButton);
        textView = findViewById(R.id.textView);
        authService = new AuthServices();

        //only for testing purpose change later
        // Set current user ID to the TextView
        FirebaseUser currentUser = authService.getCurrentUser();
        if (currentUser != null) {
            textView.setText(currentUser.getUid());
        } else {
            textView.setText("No user signed in");
        }

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authService.signOut();
                startActivity(new Intent(MainActivity.this, LoginScreen.class));
                finish();
            }
        });
    }
}