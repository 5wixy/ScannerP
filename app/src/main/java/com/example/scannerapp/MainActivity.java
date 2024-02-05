package com.example.scannerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageButton btnScan, btnView;
    String code;
    FirebaseHandler firebaseHandler;
    FirebaseFirestore db;

//TODO ADD MANUAL ADDITION
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan=findViewById(R.id.btnScan);
        btnScan.setOnClickListener(this);
        btnView = findViewById(R.id.btnView);
        btnView.setOnClickListener(this);
        firebaseHandler = new FirebaseHandler();
        db = FirebaseFirestore.getInstance();
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {

        //switch cases in menu buttons
        //Scan button Initiates scan
        //List button open item list

        switch (view.getId()){
            case R.id.btnScan:

            IntentIntegrator integrator = new IntentIntegrator(this);
                integrator.setPrompt("Scan a Barcode");
            integrator.setOrientationLocked(true);
            integrator.initiateScan();
            break;
            case R.id.btnView:
                Intent i = new Intent(this,ViewItems.class);
                startActivity(i);
                finish();
                break;

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_LONG).show();
            }

            //Checks if code exists after the scan
            else {
                if (firebaseHandler.isExist(result.getContents())) {
                    //docRef = access to Firestore items with scanned code
                    //in case there is an item with an equal code, app pops up a toast with price
                    //in case no items with code exists, app switches to item addition activity
                    DocumentReference docRef = db.collection("items").document(result.getContents());
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot doc = task.getResult();
                                if (doc.getString("name") != null) {

                                    String name = doc.getString("name");
                                    String price = doc.getString("price");
                                    //Toast.makeText(getBaseContext(), name + " costs " + price + "₪", Toast.LENGTH_LONG).show();
                                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "", Snackbar.LENGTH_INDEFINITE);
                                    View customSnackView = getLayoutInflater().inflate(R.layout.custom_snackbar_layout, null);

// Customize the TextView in your custom layout
                                    TextView textView = customSnackView.findViewById(android.R.id.text1);
                                    textView.setText(name + " costs " + price + "₪");

// Get the button in your custom layout
                                    Button btnOk = customSnackView.findViewById(R.id.btnOk);

// Set the click listener for the button
                                    btnOk.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            // Handle the "OK" button click
                                            snackbar.dismiss(); // Dismiss the Snackbar if needed
                                        }
                                    });

                                    snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
                                    Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
                                    snackbarLayout.addView(customSnackView, 0);
                                    snackbar.show();
                                } else {
                                    Log.d("Logger", "No such doc");
                                    code = result.getContents();
                                    Toast.makeText(getBaseContext(), "Insert data of item ", Toast.LENGTH_SHORT).show();
                                    switchAfterScan();
                                }

                            } else {
                                Log.d("Logger", "No such doc");
                                code = result.getContents();
                                switchAfterScan();
                                Log.d("LOGGER", "get failed with ", task.getException());
                            }
                        }
                    });
                }


                //in case of a back button press while scanning, app pops up a toast with cancelled written and goes back to menu activity


                else {
                    super.onActivityResult(requestCode, resultCode, data);

                }

            }
        }
    }
    //switches to item addition activity after scanning
    private void switchAfterScan(){
        Intent switchActInt = new Intent(this,DBActivity.class);
        switchActInt.putExtra("variable",code);
        startActivity(switchActInt);




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}