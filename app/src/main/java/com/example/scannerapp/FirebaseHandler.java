package com.example.scannerapp;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class FirebaseHandler {
    public boolean flag=true;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public FirebaseHandler() {
        this.db = db;

    }

    public void addItem(@NonNull ItemModal item) {
        /*
         * Adds StoreItem object to Firestore db,
         * uses barcode value as db identifier
         * @param item ItemModal instance.
         */

        this.db.collection("items")
                .document(item.getCode())
                .set(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
    public void getAllItems() {
        /*
         * Gets all items from the Firestore db
         */
        this.db.collection("items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d(TAG, documentSnapshot.getId() + "=> " +flag+ documentSnapshot.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents." +flag, task.getException());
                        }
                    }
                });
    }

    public void getItem(String code) {
        /*
          Gets the item by code from the Firestore db
          @param code the barcode value of the item
         */
        DocumentReference docRef = db.collection("items").document(code);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    public void editItem(String name, String price, String code,String date) {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String newDate = sdf.format(currentDate);
        DocumentReference ref = db.collection("items").document(code);
        ref.update("name",name).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("UPDATED");
            }
        });
        ref.update("price",price).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("UPDATED");
            }
        });
        ref.update("date",newDate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("UPDATED");
            }
        });



    }


    public boolean isExist(String itemCode) {
        DocumentReference docRef = db.collection("items").document(itemCode);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.getId().equals(itemCode)) {
                        flag = true;
                        Log.d(TAG, "DocSnap data: " + documentSnapshot.getData());

                    } else {
                        flag= false;
                        Log.d(TAG, "get failed with: ", task.getException());


                    }

                }
            }

        });
    return flag;
    }

    public void deleteItem(String itemCode) {
        db.collection("items").document(itemCode)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

    }
}













