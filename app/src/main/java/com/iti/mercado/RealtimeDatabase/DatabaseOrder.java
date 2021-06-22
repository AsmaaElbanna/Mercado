package com.iti.mercado.RealtimeDatabase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.iti.mercado.model.ItemPath;
import com.iti.mercado.model.Order;
import com.iti.mercado.utilities.OnDataFound;
import com.iti.mercado.utilities.OnSuccess;
import com.iti.mercado.utilities.UserFirebase;


public class DatabaseOrder {

    public static void addOrder(Order order , OnSuccess onSuccess) {

        DatabaseReference myRef = UserFirebase.getFirebaseDatabase().getReference("orders")
                .child(UserFirebase.getUserId());

        myRef.push().setValue(order).addOnCompleteListener(command -> onSuccess.change());
    }

}
