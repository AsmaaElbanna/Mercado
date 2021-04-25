package com.iti.mercado.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.mercado.R;

public class SingUpActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    private String email;
    private String password;
    private String username;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        // to  hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        TextView loginTextView = findViewById(R.id.login_TextView);
        usernameEditText = findViewById(R.id.user_name_EditText);
        emailEditText = findViewById(R.id.email_EditText);
        passwordEditText = findViewById(R.id.password_EditText);
        ImageView passwordVisibilityImageView = findViewById(R.id.password_visibility_ImageView);
        Button signButton = findViewById(R.id.sign_Button);

        passwordVisibilityImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("TAG", "onTouch: " + event.getAction());

                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN :
                        passwordEditText.setTransformationMethod(
                                HideReturnsTransformationMethod.getInstance());
                        break;
                    case MotionEvent.ACTION_UP :
                        passwordEditText.setTransformationMethod(
                                PasswordTransformationMethod.getInstance());
                        break;
                }
                return true;
            }

        });


        loginTextView.setOnClickListener(v -> {
            finish();
        });

        signButton.setOnClickListener(v -> {

            username = String.valueOf(usernameEditText.getText());
            email = String.valueOf(emailEditText.getText());
            password = String.valueOf(passwordEditText.getText());

            if (username.isEmpty()) {
                usernameEditText.setError("Add your name");
                usernameEditText.requestFocus();
            } else if (email.isEmpty()) {
                emailEditText.setError("Add your email");
                emailEditText.requestFocus();
            } else if (password.isEmpty()) {
                passwordEditText.setError("Add your password");
                passwordEditText.requestFocus();
            } else {
                createAccount();
            }

        });
    }

    private void createAccount() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            addUserToDatabase();
                            addUserToSharedPreferences();
                            //changeActivity(Home.class);
                            finishAffinity();
                        } else {
                            String errorCode =
                                    ((FirebaseAuthException) task.getException()).getErrorCode();
                            handleException(errorCode);
                        }
                    }
                });
    }

    private void changeActivity(Class<?> cls) {
        Intent homeIntent = new Intent(this, cls);
        startActivity(homeIntent);
    }

    private void addUserToDatabase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("users");

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.child(firebaseUser.getUid()).child("username").setValue(username);
    }

    private void addUserToSharedPreferences() {
        SharedPreferences sharedpreferences = this.getSharedPreferences(
                "user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }

    private void handleException(String errorCode) {

        switch (errorCode) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                createToast(getResources().getString(R.string.ERROR_INVALID_CUSTOM_TOKEN));
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                createToast(getResources().getString(R.string.ERROR_CUSTOM_TOKEN_MISMATCH));
                break;

            case "ERROR_INVALID_CREDENTIAL":
                createToast(getResources().getString(R.string.ERROR_INVALID_CREDENTIAL));
                break;

            case "ERROR_INVALID_EMAIL":
                emailEditText.setError(getResources().getString(R.string.ERROR_INVALID_EMAIL));
                emailEditText.requestFocus();
                emailEditText.setSelection(emailEditText.getText().length());
                break;

            case "ERROR_WRONG_PASSWORD":
                passwordEditText.setError(getResources().getString(R.string.ERROR_WRONG_PASSWORD));
                passwordEditText.requestFocus();
                passwordEditText.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                createToast(getResources().getString(R.string.ERROR_USER_MISMATCH));
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                createToast(getResources().getString(R.string.ERROR_REQUIRES_RECENT_LOGIN));
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                createToast(getResources()
                        .getString(R.string.ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL));
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                emailEditText.setError(getResources()
                        .getString(R.string.ERROR_EMAIL_ALREADY_IN_USE));
                emailEditText.requestFocus();
                emailEditText.setSelection(emailEditText.getText().length());
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                createToast(getResources().getString(R.string.ERROR_CREDENTIAL_ALREADY_IN_USE));
                break;

            case "ERROR_USER_DISABLED":
                createToast(getResources().getString(R.string.ERROR_USER_DISABLED));
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                createToast(getResources().getString(R.string.ERROR_USER_TOKEN_EXPIRED));
                break;

            case "ERROR_USER_NOT_FOUND":
                createToast(getResources().getString(R.string.ERROR_USER_NOT_FOUND));
                break;

            case "ERROR_INVALID_USER_TOKEN":
                createToast(getResources().getString(R.string.ERROR_INVALID_USER_TOKEN));
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                createToast(getResources().getString(R.string.ERROR_OPERATION_NOT_ALLOWED));
                break;

            case "ERROR_WEAK_PASSWORD":
                passwordEditText.setError(getResources().getString(R.string.ERROR_WEAK_PASSWORD));
                passwordEditText.requestFocus();
                passwordEditText.setText("");
                break;
            default:
                createToast(getResources().getString(R.string.NO_INTERNET_CONNECTION));
        }
    }

    private void createToast(String text) {
        Toast.makeText(this, text,
                Toast.LENGTH_SHORT).show();
    }
}