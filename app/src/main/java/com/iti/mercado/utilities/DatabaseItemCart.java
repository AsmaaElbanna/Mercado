package com.iti.mercado.utilities;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.iti.mercado.model.Cart;
import com.iti.mercado.model.Item;

public class DatabaseItemCart {

    public static void getItemDetails(Cart cart,
                                      Class<? extends Item> cls,
                                      OnRetrieveItem onRetrieveItem) {
        DatabaseReference myRef = UserFirebase.getFirebaseDatabase().getReference("categories")
                .child(cart.getCategory())
                .child(cart.getSubCategory())
                .child(cart.getItemId());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                cart.setItem(dataSnapshot.getValue(cls));
                onRetrieveItem.onRetrieveItems();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB", "Failed to read value.", error.toException());
            }
        });
    }
}
