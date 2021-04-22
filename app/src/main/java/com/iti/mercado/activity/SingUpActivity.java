package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.mercado.R;

public class SingUpActivity extends AppCompatActivity {

    private TextView loginTextView;
    private EditText userNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private ImageView password_visibility_ImageView;
    private Button signButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        // to  hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        loginTextView = findViewById(R.id.login_TextView);
        userNameEditText = findViewById(R.id.user_name_EditText);
        emailEditText = findViewById(R.id.email_EditText);
        passwordEditText = findViewById(R.id.password_EditText);
        password_visibility_ImageView = findViewById(R.id.password_visibility_ImageView);
        signButton = findViewById(R.id.sign_Button);


        loginTextView.setOnClickListener(v -> {
            Intent homeIntent = new Intent(SingUpActivity.this, LoginActivity.class);
            startActivity(homeIntent);
        });
    }
}