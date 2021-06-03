package com.iti.mercado.RealtimeDatabase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.iti.mercado.model.ItemPath;
import com.iti.mercado.utilities.OnRetrieveItems;
import com.iti.mercado.utilities.UserFirebase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DatabaseItems {

    public static void getItems(String reference,
                                OnRetrieveItems retrieveItems) {

        DatabaseReference myRef = UserFirebase.getFirebaseDatabase().getReference(reference);

        List<ItemPath> itemPaths = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshots) {

                for (DataSnapshot dataSnapshot : dataSnapshots.getChildren()) {
                    itemPaths.add(dataSnapshot.getValue(ItemPath.class));
                }
                retrieveItems.getItems(itemPaths);
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                // Failed to read value
                Log.w("DB", "Failed to read value.", error.toException());
            }
        });
    }
}
