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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.mercado.R;

public class LoginActivity extends AppCompatActivity {

    private final int RC_SIGN_IN = 911;

    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;

    private String email;
    private String password;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // to  hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        TextView signUpTextView = findViewById(R.id.sign_TextView);

        emailInputLayout = findViewById(R.id.email_EditText);
        passwordInputLayout = findViewById(R.id.password_EditText);
        TextView forgotPasswordTextView = findViewById(R.id.forgot_password_TextView);
        //ImageView passwordVisibilityImageView = findViewById(R.id.password_visibility_ImageView);

        Button loginEmailPasswordButton = findViewById(R.id.login_Button);
        Button loginGoogleButton = findViewById(R.id.login_google_Button);

        /*passwordVisibilityImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("TAG", "onTouch: " + event.getAction());

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        passwordInputLayout.getEditText().setTransformationMethod(
                                HideReturnsTransformationMethod.getInstance());
                        passwordInputLayout.getEditText().setSelection(passwordInputLayout.getEditText().getText().length());

                        break;
                    case MotionEvent.ACTION_UP:
                        passwordInputLayout.getEditText().setTransformationMethod(
                                PasswordTransformationMethod.getInstance());
                        passwordInputLayout.getEditText().setSelection(passwordInputLayout.getEditText().getText().length());
                        break;
                }
                return true;
            }

        });*/

        signUpTextView.setOnClickListener(v -> {
            changeActivity(SingUpActivity.class);
        });

        forgotPasswordTextView.setOnClickListener(v -> {

            String emailAddress = emailInputLayout.getEditText().getText().toString();

            if (!emailAddress.isEmpty()) {

                FirebaseAuth auth = FirebaseAuth.getInstance();

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d("TAG", "Email sent.");
                                Toast.makeText(LoginActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Task not Successful", Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        });

        loginEmailPasswordButton.setOnClickListener(v -> {

            emailInputLayout.setError(null);
            passwordInputLayout.setError(null);

            email = String.valueOf(emailInputLayout.getEditText().getText());
            password = String.valueOf(passwordInputLayout.getEditText().getText());

            if (email.isEmpty()) {
                emailInputLayout.setError("Add your email");
                emailInputLayout.requestFocus();
            } else if (password.isEmpty()) {
                passwordInputLayout.setError("Add your password");
                passwordInputLayout.requestFocus();
            } else {
                signInEmail();
            }
        });

        loginGoogleButton.setOnClickListener(v -> {
            signInGoogle();
        });
    }

    private void signInEmail() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            changeActivity(TabsActivity.class);
                            getUserFromDatabase();
                            //finishAffinity();
                        } else {
                            String errorCode =
                                    ((FirebaseAuthException) task.getException()).getErrorCode();
                            handleException(errorCode);
                        }
                    }
                });
    }

    private void signInGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account.getIdToken());
                }
            } catch (ApiException e) {
                Log.e("appError", "onActivityResult: " + e.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            changeActivity(TabsActivity.class);
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
                emailInputLayout.setError(getResources().getString(R.string.ERROR_INVALID_EMAIL));
                emailInputLayout.requestFocus();
                emailInputLayout.getEditText().setSelection(emailInputLayout.getEditText().getText().length());
                break;

            case "ERROR_WRONG_PASSWORD":
                passwordInputLayout.setError(getResources().getString(R.string.ERROR_WRONG_PASSWORD));
                passwordInputLayout.requestFocus();
                passwordInputLayout.getEditText().setText("");
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
                emailInputLayout.setError(getResources()
                        .getString(R.string.ERROR_EMAIL_ALREADY_IN_USE));
                emailInputLayout.requestFocus();
                emailInputLayout.getEditText().setSelection(emailInputLayout.getEditText().getText().length());
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
                passwordInputLayout.setError(getResources().getString(R.string.ERROR_WEAK_PASSWORD));
                passwordInputLayout.requestFocus();
                passwordInputLayout.getEditText().setText("");
                break;
            default:
                createToast(getResources().getString(R.string.NO_INTERNET_CONNECTION));
        }
    }

    private void createToast(String text) {
        Toast.makeText(this, text,
                Toast.LENGTH_SHORT).show();
    }

    private void getUserFromDatabase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("users");

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.child(firebaseUser.getUid()).child("username")
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()) {
                    addUserToSharedPreferences((String) task.getResult().getValue());
                }
            }
        });
    }

    private void addUserToSharedPreferences(String username) {
        SharedPreferences sharedpreferences = this.getSharedPreferences(
                "user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }
}