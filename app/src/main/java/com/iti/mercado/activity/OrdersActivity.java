package com.iti.mercado.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.mercado.R;
import com.iti.mercado.RealtimeDatabase.DatabaseItems;
import com.iti.mercado.adapter.OrderAdapter;
import com.iti.mercado.model.ItemPath;
import com.iti.mercado.model.Order;
import com.iti.mercado.utilities.UserFirebase;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    private ImageView backArrow;
    private RecyclerView recyclerView;
    private List<Order> orderList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        // to  hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        backArrow = findViewById(R.id.back_button);
        recyclerView = findViewById(R.id.ordersRecyclerView);
        orderList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("orders").child(UserFirebase.getUserId());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        OrderAdapter orderAdapter = new OrderAdapter(OrdersActivity.this, orderList);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int x = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    orderList.add(dataSnapshot.getValue(Order.class));
                    orderList.get(x).setId(dataSnapshot.getKey());
                    Log.i("TAG", "onDataChange: ddddddd" + dataSnapshot.getKey());
                    x++;
                }
                recyclerView.setAdapter(orderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("DB", "Failed to read value.", error.toException());
            }
        });

        backArrow.setOnClickListener(v -> {
            finish();
        });
    }
}