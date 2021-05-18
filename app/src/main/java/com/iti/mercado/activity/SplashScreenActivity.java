package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.iti.mercado.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // to  hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        final int timeSplash = 5000; // 1000 = 1 sec

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent  = new Intent(SplashScreenActivity.this , GetStartedActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, timeSplash) ;

    }
}