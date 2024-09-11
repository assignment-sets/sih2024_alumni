package com.example.alumnihub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.alumnihub.MainActivity;
import com.example.alumnihub.R;

public class SignUpScreen extends AppCompatActivity {
    private Button logInBtn, createActBtn;
    private TextView signUp, haveAct;
    private AutoCompleteTextView emailField;
    private EditText userNameField, passwordField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        logInBtn = findViewById(R.id.login_btn_ca);
        createActBtn = findViewById(R.id.create_account_btn_ca);
        signUp = findViewById(R.id.signUpTxt);
        haveAct = findViewById(R.id.haveAcnt);
        emailField = findViewById(R.id.email_enter_ca);
        userNameField = findViewById(R.id.username_ca);
        passwordField = findViewById(R.id.password_enter_ca);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpScreen.this, LoginScreen.class));
                finish();
            }
        });
    }
}