package com.iti.mercado.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.iti.mercado.model.Address;
import com.iti.mercado.utilities.UserFirebase;

import org.jetbrains.annotations.NotNull;

public class CheckoutActivity extends AppCompatActivity {
    private TextView countryTextView;
    private TextView governorateTextView;
    private TextView areaTextView;
    private TextView streetTextView;
    private TextView building;
    private RelativeLayout addressEditRelativeLayout;
    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // to  hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

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

}