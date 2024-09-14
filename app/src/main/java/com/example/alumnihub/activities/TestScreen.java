package com.example.alumnihub.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.alumnihub.R;
import com.example.alumnihub.backend_services.firestore_db.UserServicesDB;
import com.example.alumnihub.data_models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class TestScreen extends AppCompatActivity {

    private UserServicesDB userServicesDB;
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
        userServicesDB = new UserServicesDB();

        // Retrieve the User object from the Intent
        User user = (User) getIntent().getSerializableExtra("user");
        if (user != null) {
            // Display the User details in the TextView
            String userDetails = "Username: " + user.getUserName() + "\n" +
                    "Email: " + user.getEmail() + "\n" +
                    "Full Name: " + user.getFullName();
            resultTextView.setText(userDetails);
        } else {
            resultTextView.setText("No user data available");
        }
    }
}