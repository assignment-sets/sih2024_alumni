package com.example.alumnihub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.alumnihub.MainActivity;
import com.example.alumnihub.R;
import com.example.alumnihub.backend_services.firebase_auth.AuthServices;
import com.example.alumnihub.backend_services.firestore_db.UserServicesDB;
import com.example.alumnihub.utils.ValidationUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignUpScreen extends AppCompatActivity {
    private Button logInBtn, createActBtn;
    private TextView signUp, haveAct;
    private AutoCompleteTextView emailField;
    private EditText userNameField, passwordField;
    private AuthServices authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_screen);

        logInBtn = findViewById(R.id.login_btn_ca);
        createActBtn = findViewById(R.id.create_account_btn_ca);
        signUp = findViewById(R.id.signUpTxt);
        haveAct = findViewById(R.id.haveAcnt);
        emailField = findViewById(R.id.email_enter_ca);
        userNameField = findViewById(R.id.username_ca);
        passwordField = findViewById(R.id.password_enter_ca);
        authService = new AuthServices();

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpScreen.this, LoginScreen.class));
                finish();
            }
        });

        createActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignUp();
            }
        });
    }

    /**
     * Handles the sign-up process.
     */
    private void handleSignUp() {
        String username = userNameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        // Validate input
        if (!ValidationUtils.isValidUsername(username)) {
            showToast("Invalid username");
            return;
        }
        if (!ValidationUtils.isValidEmail(email)) {
            showToast("Invalid email format");
            return;
        }
        if (!ValidationUtils.isValidPassword(password)) {
            showToast("Password too weak, must be 6 characters or more.");
            return;
        }

        // Perform sign-up
        authService.signUp(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = authService.getCurrentUser();
                    if (currentUser != null) {
                        UserServicesDB userServices = new UserServicesDB();
                        userServices.addUser(currentUser.getUid(), username, currentUser.getEmail())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            showToast("Sign-up successful");
                                            startActivity(new Intent(SignUpScreen.this, AdditionalDetailsFormScreen.class));
                                            finish();
                                        } else {
                                            showToast("Failed to add user details: " + Objects.requireNonNull(task.getException()).getMessage());
                                        }
                                    }
                                });
                    } else {
                        showToast("Sign-up successful, but unable to fetch user details.");
                    }
                } else {
                    showToast("Sign-up failed: " + Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });
    }

    /**
     * Displays a toast message.
     *
     * @param message The message to display.
     */
    private void showToast(String message) {
        Toast.makeText(SignUpScreen.this, message, Toast.LENGTH_SHORT).show();
    }
}