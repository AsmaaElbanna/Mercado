package com.iti.mercado.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.mercado.R;
import com.iti.mercado.RealtimeDatabase.DatabaseGetItemById;
import com.iti.mercado.RealtimeDatabase.DatabaseOrder;
import com.iti.mercado.adapter.CartAdapter;
import com.iti.mercado.model.Address;
import com.iti.mercado.model.Cart;
import com.iti.mercado.model.Item;
import com.iti.mercado.model.Order;
import com.iti.mercado.model.OrderItem;
import com.iti.mercado.utilities.CountSubPrice;
import com.iti.mercado.utilities.OnSuccess;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity implements OnSuccess {
    private ImageView backArrow;
    private TextView countryTextView;
    private TextView governorateTextView;
    private TextView areaTextView;
    private TextView streetTextView;
    private TextView building;
    private RelativeLayout addressEditRelativeLayout;
    private Address address;
    private ArrayList<Cart> carts;
    private TextView itemCount;
    private RecyclerView recyclerView;
    private Double subTotal = 0.0;
    private TextView totalPriceTextView;
    private Button placeOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // to  hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        backArrow = findViewById(R.id.back_button);

        totalPriceTextView = findViewById(R.id.total_price);

        carts = (ArrayList<Cart>) getIntent().getSerializableExtra("carts");

        placeOrderButton = findViewById(R.id.place_order);
        itemCount = findViewById(R.id.item_number);

        CartAdapter cartAdapter;

        recyclerView = findViewById(R.id.item_list);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        if (carts == null) {
            CardView addressEdit = findViewById(R.id.cardViewAddress);
            TextView headerTitle = findViewById(R.id.headerTitle);

            addressEdit.setVisibility(View.GONE);
            headerTitle.setText("Order Details");
            placeOrderButton.setVisibility(View.GONE);


            Order order = (Order) getIntent().getSerializableExtra("order");

            TextView shippingAddress = findViewById(R.id.shippingAddress);
            shippingAddress.setVisibility(View.VISIBLE);
            shippingAddress.setText(order.getShippingAddress());

            itemCount.setText(String.valueOf(order.getItems().size()));

            List<Cart> carts = new ArrayList<>();

            cartAdapter = new CartAdapter(this, carts, null);

            recyclerView.setAdapter(cartAdapter);

            for(OrderItem orderItem: order.getItems()) {

                DatabaseGetItemById.getItem(orderItem.getItemId(), carts, orderItem.getCount(), () -> {
                    cartAdapter.notifyDataSetChanged();
                });
            }

        } else {
            placeOrderButton.setOnClickListener(v -> {
                if (address != null) {
                    Order order = new Order();
                    order.setTotalPrice(subTotal + 50);
                    order.setItems(Cart.getOrderItems(carts));
                    order.setShippingAddress(address.getBuilding() + ", " + address.getStreet() + ", "
                            + address.getArea() + ", " + address.getGovernorate() + ", "
                            + address.getCountry());

                    DatabaseOrder.addOrder(order, this);
                } else {
                    Toast.makeText(this, "Add Your Address", Toast.LENGTH_LONG).show();
                }

            });

            itemCount.setText(String.valueOf(carts.size()));


            cartAdapter = new CartAdapter(this, carts, null);

            recyclerView.setAdapter(cartAdapter);

            countryTextView = findViewById(R.id.country);
            governorateTextView = findViewById(R.id.governorate);
            areaTextView = findViewById(R.id.area);
            streetTextView = findViewById(R.id.street);
            building = findViewById(R.id.building);
            addressEditRelativeLayout = findViewById(R.id.address_edit);
            address = new Address();
            getAddressData();

            addressEditRelativeLayout.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Alert")
                        .setMessage("Are you want to Edit The Address")
                        .setPositiveButton("Yes", (dialog1, which) -> {
                            Intent intent = new Intent(CheckoutActivity.this, DeliveryActivity.class);
                            startActivity(intent);
                        })
                        .setNegativeButton("No", (dialog1, which) -> {

                        })
                        .show();

            });
        }

        subTotal = getIntent().getDoubleExtra("subTotal", 0.0);
        totalPriceTextView.setText(subTotal + " EGP");




        backArrow.setOnClickListener(v -> {
            finish();
        });
    }

    private void getAddressData() {

        DatabaseReference addressReference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("address");

        addressReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                address = snapshot.getValue(Address.class);
                if (address != null) { // has data
                    displayDataIntoScreen();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void displayDataIntoScreen() {
        countryTextView.setText(address.getCountry());
        governorateTextView.setText(address.getGovernorate());
        areaTextView.setText(address.getArea());
        streetTextView.setText(address.getStreet());
        building.setText(address.getBuilding());
    }

    @Override
    public void change() {
        Intent intent = new Intent(this, SuccessActivity.class);
        startActivity(intent);
        finish();
    }
}