package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.iti.mercado.R;

public class AboutActivity extends AppCompatActivity {
    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // to  hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        backArrow = findViewById(R.id.back_button);




        backArrow.setOnClickListener(v -> {
            finish();
        });
    }
}