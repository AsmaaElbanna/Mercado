package com.iti.mercado.utilities;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.iti.mercado.model.FlashSale;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DatabaseFlashSale {

    public static void getImages(OnRetrieveFlashSale onRetrieveFlashSale) {

        DatabaseReference myRef = UserFirebase.getFirebaseDatabase().getReference("flashSale");

        List<FlashSale> flashSales = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshots) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot dataSnapshot : dataSnapshots.getChildren()) {
                    flashSales.add(dataSnapshot.getValue(FlashSale.class));
                }
                onRetrieveFlashSale.getFlashSaleList(flashSales);
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                // Failed to read value
                Log.w("DB", "Failed to read value.", error.toException());
            }
        });
    }
}
