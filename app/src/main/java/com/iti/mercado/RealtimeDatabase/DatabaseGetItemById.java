package com.iti.mercado.RealtimeDatabase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.iti.mercado.model.Cart;
import com.iti.mercado.model.Item;
import com.iti.mercado.utilities.OnRetrieveItem;
import com.iti.mercado.utilities.UserFirebase;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DatabaseGetItemById {

    public static void getItem(String itemId, List<Cart> carts, int count, OnRetrieveItem onRetrieveItem) {

        DatabaseReference myRef = UserFirebase.getFirebaseDatabase().getReference("allItems")
                .child(itemId);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                Cart cart = new Cart();
                cart.setItem(dataSnapshot.getValue(Item.class));
                cart.setCount(count);
                carts.add(cart);

                onRetrieveItem.onRetrieveItems();
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                // Failed to read value
                Log.w("DB", "Failed to read value.", error.toException());
            }
        });
    }
}
