package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.iti.mercado.R;

public class GetStartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        // to  hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        final Button getStartedButton = findViewById(R.id.get_started_Button);

        getStartedButton.setOnClickListener(v -> {
            Intent homeIntent = new Intent(GetStartedActivity.this,
                    LoginActivity.class);
            startActivity(homeIntent);
            finish();
        });
    }
}