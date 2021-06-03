package com.iti.mercado.utilities;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.iti.mercado.model.ItemPath;

import java.util.ArrayList;


public class DatabaseFavorite {


    public void write(ItemPath favoriteItem, OnSuccess onSuccess) {
        DatabaseReference myRef = UserFirebase.getFirebaseDatabase().getReference("favorite")
                .child(UserFirebase.getUserId())
                .child(favoriteItem.getItemId());
        myRef.setValue(favoriteItem).addOnCompleteListener(command -> onSuccess.change());

    }

    public void read(ItemPath favoriteItem, OnDataFound onDataFound) {
        DatabaseReference myRef = UserFirebase.getFirebaseDatabase().getReference("favorite")
                .child(UserFirebase.getUserId())
                .child(favoriteItem.getItemId());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                onDataFound.dataFound(dataSnapshot.exists());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB", "Failed to read value.", error.toException());
            }
        });
    }

    public void delete(ItemPath favoriteItem, OnSuccess onSuccess) {
        DatabaseReference myRef = UserFirebase.getFirebaseDatabase().getReference("favorite")
                .child(UserFirebase.getUserId())
                .child(favoriteItem.getItemId());
        myRef.removeValue().addOnCompleteListener(command -> onSuccess.change());

    }

    public static void getAllItems(ArrayList<ItemPath> favoriteItems,
                                   OnRetrieveFavoriteItems onRetrieveFavoriteItems) {
        DatabaseReference myRef = UserFirebase.getFirebaseDatabase().getReference("favorite")
                .child(UserFirebase.getUserId());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshots) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot dataSnapshot : dataSnapshots.getChildren()) {
                    favoriteItems.add(dataSnapshot.getValue(ItemPath.class));
                }
                onRetrieveFavoriteItems.onRetrieveItems();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("DB", "Failed to read value.", error.toException());
            }
        });
    }
}
