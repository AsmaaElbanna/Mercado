package com.iti.mercado.utilities;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.iti.mercado.model.ItemPath;
import com.iti.mercado.model.Item;

public class DatabaseItem {

    public static void getItemDetails(ItemPath favoriteItem,
                                      Class<? extends Item> cls,
                                      OnRetrieveItem onRetrieveItem) {
        DatabaseReference myRef = UserFirebase.getFirebaseDatabase().getReference("categories")
                .child(favoriteItem.getCategory())
                .child(favoriteItem.getSubCategory())
                .child(favoriteItem.getItemId());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                favoriteItem.setItem(dataSnapshot.getValue(cls));
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
