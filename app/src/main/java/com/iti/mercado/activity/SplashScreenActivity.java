package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iti.mercado.R;
import com.iti.mercado.utilities.Constants;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // to  hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentFirebaseUser == null)
                    changeActivity(GetStartedActivity.class);
                else
                    changeActivity(TabsActivity.class);
                finish();
            }
        }, Constants.TIME_SPLASH);

    }

    private void changeActivity(Class<?> cls) {
        Intent homeIntent = new Intent(this, cls);
        startActivity(homeIntent);
    }
}