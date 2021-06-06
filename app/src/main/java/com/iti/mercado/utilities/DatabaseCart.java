package com.iti.mercado.utilities;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.iti.mercado.model.Cart;

import java.util.ArrayList;

public class DatabaseCart {

    public void write(Cart cart, OnSuccess onSuccess) {
        DatabaseReference myRef = UserFirebase.getFirebaseDatabase().getReference("cart")
                .child(UserFirebase.getUserId())
                .child(cart.getItemId());
        myRef.setValue(cart).addOnCompleteListener(command -> onSuccess.change());
    }

    public void Read(Cart cart, OnDataFound onDataFound) {
        DatabaseReference myRef = UserFirebase.getFirebaseDatabase().getReference("cart")
                .child(UserFirebase.getUserId())
                .child(cart.getItemId());

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

//    public void delete(FavoriteItem favoriteItem, OnSuccess onSuccess) {
//        DatabaseReference myRef = UserFirebase.getFirebaseDatabase().getReference("favorite")
//                .child(UserFirebase.getUserId())
//                .child(favoriteItem.getItemId());
//        myRef.removeValue().addOnCompleteListener(command -> onSuccess.change());
//
//    }



    public void updateCount(int count,String item_id){
        DatabaseReference myRef = UserFirebase.getFirebaseDatabase().getReference("cart")
                .child(UserFirebase.getUserId())
                .child(item_id);
        myRef.child("count").setValue(count);
    }

    public static void getAllItems(ArrayList<Cart> carts,
                                   OnRetrieveFavoriteItems onRetrieveFavoriteItems) {
        DatabaseReference myRef = UserFirebase.getFirebaseDatabase().getReference("cart")
                .child(UserFirebase.getUserId());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshots) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot dataSnapshot : dataSnapshots.getChildren()) {
                    carts.add(dataSnapshot.getValue(Cart.class));
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
