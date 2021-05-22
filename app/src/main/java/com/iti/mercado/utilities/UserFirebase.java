package com.iti.mercado.utilities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserFirebase {
    private static String userId = "";
    private static FirebaseDatabase firebaseDatabase;


    public static String getUserId() {
        if (userId.equals("")) {
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        return userId;
    }

    public static FirebaseDatabase getFirebaseDatabase() {
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
        }
        return firebaseDatabase;
    }
}
