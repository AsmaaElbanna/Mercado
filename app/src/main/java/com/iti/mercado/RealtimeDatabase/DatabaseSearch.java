package com.iti.mercado.RealtimeDatabase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iti.mercado.model.Item;
import com.iti.mercado.model.ItemPath;
import com.iti.mercado.utilities.OnRetrieveItems;
import com.iti.mercado.utilities.UserFirebase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DatabaseSearch {

    static public void searchItemsWithName(String name, OnRetrieveItems retrieveItems) {
        DatabaseReference databaseReference = UserFirebase.getFirebaseDatabase()
                .getReference("allItems");
        Query query = databaseReference.orderByChild("lowerCaseTitle")
                .startAt(name.toLowerCase()).limitToFirst(10);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshots) {

                ArrayList<ItemPath> itemPaths = new ArrayList<>();
                for (DataSnapshot dataSnapshot : dataSnapshots.getChildren()) {
                    ItemPath itemPath = dataSnapshot.getValue(ItemPath.class);
                    itemPath.setItem(dataSnapshot.getValue(Item.class));
                    itemPaths.add(itemPath);
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
