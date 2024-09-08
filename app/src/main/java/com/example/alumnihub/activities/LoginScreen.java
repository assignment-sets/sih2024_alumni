package com.example.alumnihub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.alumnihub.R;

public class LoginScreen extends AppCompatActivity {
    private Button logInBtn,createAccountBtn;
    private TextView welcomeTxt, connectTxt, dontHaveActTxt;
    private EditText emailTxtField, passwordTxtField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        logInBtn = findViewById(R.id.login_button);
        createAccountBtn = findViewById(R.id.create_account_button);
        welcomeTxt = findViewById(R.id.WelcomeBackText);
        connectTxt = findViewById(R.id.connectMsgTxt);
        dontHaveActTxt = findViewById(R.id.no_account_text);
        emailTxtField = findViewById(R.id.email_input);
        passwordTxtField = findViewById(R.id.password_input);

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, SignUpScreen.class));
                finish();
            }
        });
    }
}