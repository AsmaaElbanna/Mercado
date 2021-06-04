package com.iti.mercado.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.app.ActionBar;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.iti.mercado.R;

public class SearchActivity extends AppCompatActivity {

    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditText = findViewById(R.id.search);
        searchEditText.setPressed(true);
        searchEditText.setSelection(0);
        searchEditText.requestFocus();

//        if (Build.VERSION.SDK_INT >= 23) {
//            InputMethodManager inputMethodManager =
//                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        if (id==android.R.id.home) {
//            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
//                    .makeSceneTransitionAnimation(
//                            this,
//                            searchEditText,
//                            ViewCompat.getTransitionName(searchEditText));
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}