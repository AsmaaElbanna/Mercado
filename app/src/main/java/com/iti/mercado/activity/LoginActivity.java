package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.mercado.R;

public class LoginActivity extends AppCompatActivity {
    private TextView signUpTextView;
    private EditText emailEditText;
    private EditText passwordEditText;
    private ImageView password_visibility_ImageView;
    private Button loginButton;
    private Button login_google_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // to  hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        signUpTextView = findViewById(R.id.sign_TextView);
        emailEditText = findViewById(R.id.email_EditText);
        passwordEditText = findViewById(R.id.password_EditText);
        password_visibility_ImageView = findViewById(R.id.password_visibility_ImageView);
        loginButton = findViewById(R.id.login_Button);
        loginButton = findViewById(R.id.login_google_Button);

        signUpTextView.setOnClickListener(v -> {
            Intent homeIntent = new Intent(LoginActivity.this, SingUpActivity.class);
            startActivity(homeIntent);
        });
    }
}