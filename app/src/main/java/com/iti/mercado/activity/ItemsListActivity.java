package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.iti.mercado.R;
import com.iti.mercado.utilities.Network;

public class ItemsListActivity extends AppCompatActivity {

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        recyclerView = findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //Network.parsLaptops(this,recyclerView);
        //Network.parsLaptopBags(this,recyclerView);
        //Network.parsMobiles(this,recyclerView);
        Network.parsMicrowaves(this,recyclerView);
        //Network.parsBlendersAndMixers(this,recyclerView);
    }

}