package com.iti.mercado.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.iti.mercado.R;
import com.iti.mercado.RealtimeDatabase.DatabaseSearch;
import com.iti.mercado.adapter.SearchAdapter;
import com.iti.mercado.model.ItemPath;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText searchEditText;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.searchResultRecyclerView);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        searchAdapter = new SearchAdapter(SearchActivity.this, null);
        recyclerView.setAdapter(searchAdapter);


        searchEditText = findViewById(R.id.search);
        searchEditText.setPressed(true);
        searchEditText.setSelection(0);
        searchEditText.requestFocus();

        searchEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                DatabaseSearch.searchItemsWithName(searchEditText.getText().toString(),
                        itemsPaths -> {
                            Log.i("TAG", "afterTextChanged: " + itemsPaths);
                    SearchActivity.this.searchAdapter.setItemPaths(itemsPaths);
                    SearchActivity.this.searchAdapter.notifyDataSetChanged();
                });
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

//        if (Build.VERSION.SDK_INT >= 23) {
//            InputMethodManager inputMethodManager =
//                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            ActivityCompat.finishAfterTransition(this);
        }
        return super.onOptionsItemSelected(item);
    }
}