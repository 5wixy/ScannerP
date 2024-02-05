package com.example.scannerapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Random;

public class DBActivity extends AppCompatActivity{

    // creating variables for our edittext, button and dbhandler
    private Button addItemBtn;
    TextView tvCode;
    EditText etName;
    EditText etPrice;
    MyAdapter adapter;
    public String itemName,rCode,itemPrice,itemDate;
    public Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbactivity);
        // initializing all our variables.
        tvCode = findViewById(R.id.code);
        etName = findViewById(R.id.name);
        etPrice = findViewById(R.id.price);
        etName.setInputType(InputType.TYPE_CLASS_TEXT);
        etPrice.setInputType(InputType.TYPE_CLASS_PHONE);
        addItemBtn = findViewById(R.id.btnAdd);
        itemName =etName.getText().toString();
        itemPrice = etPrice.getText().toString();
        // creating a new dbhandler class
        // and passing our context to it.
        FirebaseHandler firebaseHandler = new FirebaseHandler();
        intent = getIntent();
        rCode = intent.getStringExtra("variable");
        tvCode.setText(rCode);



        // below line is to add on click listener for our add course button.
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // below line is to get data from all edit text fields.
                itemName = etName.getText().toString();
                itemPrice = etPrice.getText().toString();
                Date currentDate = new Date();

                // Create a SimpleDateFormat object with the desired format
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                // Format the date and convert it to a string
                String itemDate = sdf.format(currentDate);

                // validating if the text fields are empty or not.
                if (itemName.isEmpty() || itemPrice.isEmpty()) {
                    Toast.makeText(DBActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    if (rCode == null) {
                        Random rnd = new Random();
                        rCode = String.valueOf(rnd.nextInt(999999999));
                        firebaseHandler.addItem(new ItemModal(rCode, itemName, itemPrice,itemDate));
                    }


                    }
                    firebaseHandler.addItem(new ItemModal(rCode, itemName, itemPrice,itemDate));

                    // calling a method to add new
                    // course to sqlite data and pass all our values to it.
                    // after adding the data we are displaying a toast message.
                    Toast.makeText(DBActivity.this, "Item has been added.", Toast.LENGTH_SHORT).show();
                    Intent swapAct = new Intent(DBActivity.this, ViewItems.class);
                    intent.putExtra("SCROLL",rCode);
                    startActivity(swapAct);
                    //TODO ADD SCROLL TO ITEM WHEN ADDED
                }


        });
}
    //Navigation button press kills app to prevent main activity stack
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, ViewItems.class);
        startActivity(i);
    }

}