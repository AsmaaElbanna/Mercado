package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.iti.mercado.R;

public class SuccessActivity extends AppCompatActivity {
    private Button goHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        goHome = findViewById(R.id.main_button);

        goHome.setOnClickListener(v -> {
            Intent homeIntent = new Intent(SuccessActivity.this, TabsActivity.class);
            startActivity(homeIntent);
            finish();
        });
    }
}