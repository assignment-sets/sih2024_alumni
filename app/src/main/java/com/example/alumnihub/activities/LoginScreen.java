package com.example.alumnihub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.annotation.NonNull;

import com.example.alumnihub.R;
import com.example.alumnihub.backend_services.firebase_auth.AuthServices;
import com.example.alumnihub.utils.ValidationUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.Objects;

public class LoginScreen extends AppCompatActivity {
    private Button logInBtn, createAccountBtn;
    private TextView welcomeTxt, connectTxt, dontHaveActTxt;
    private EditText emailTxtField, passwordTxtField;
    private AuthServices authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);

        logInBtn = findViewById(R.id.login_button);
        createAccountBtn = findViewById(R.id.create_account_button);
        welcomeTxt = findViewById(R.id.WelcomeBackText);
        connectTxt = findViewById(R.id.connectMsgTxt);
        dontHaveActTxt = findViewById(R.id.no_account_text);
        emailTxtField = findViewById(R.id.email_input);
        passwordTxtField = findViewById(R.id.password_input);
        authService = new AuthServices();

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignIn();
            }
        });

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, SignUpScreen.class));
                finish();
            }
        });
    }

    /**
     * Handles the sign-in process.
     */
    private void handleSignIn() {
        String email = emailTxtField.getText().toString().trim();
        String password = passwordTxtField.getText().toString().trim();

        // Validate input
        if (!ValidationUtils.isValidEmail(email)) {
            showToast("Invalid email format");
            return;
        }
        if (!ValidationUtils.isValidPassword(password)) {
            showToast("Password too weak, must be 6 characters or more.");
            return;
        }

        // Perform sign-in
        authService.signIn(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    showToast("Sign-in successful");
                    startActivity(new Intent(LoginScreen.this, MainActivity.class));
                    finish();
                } else {
                    showToast("Sign-in failed: " + Objects.requireNonNull(task.getException()).getMessage());
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
        Toast.makeText(LoginScreen.this, message, Toast.LENGTH_SHORT).show();
    }
}