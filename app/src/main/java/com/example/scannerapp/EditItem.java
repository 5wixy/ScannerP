package com.example.scannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditItem extends AppCompatActivity {
    EditText etName,etPrice;
    Button btnEdit,btnCancel;
    Intent intent;
    String name;
    String price;
    String code;
    String date;
    FirebaseHandler firebaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        TextView tvCode = findViewById(R.id.code);
        TextView tvDate = findViewById(R.id.tvDate);
        firebaseHandler = new FirebaseHandler();
        etName = findViewById(R.id.EditName);
        etPrice = findViewById(R.id.EditPrice);
        btnCancel = findViewById(R.id.btnCancel);
        btnEdit = findViewById(R.id.btnEdit);
        intent = getIntent();
        name = intent.getStringExtra("EDITName");
        price = intent.getStringExtra("EDITPrice");
        code = intent.getStringExtra("EDITCode");
        tvCode.setText(code);
        date = intent.getStringExtra("EDITDate");
        tvDate.setText(date);
        etName.setText(name);
        etPrice.setText(price);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etName.getText().toString();
                price = etPrice.getText().toString();
                firebaseHandler.editItem(name,price,code,date);
                Intent i = new Intent(EditItem.this,ViewItems.class);
                startActivity(i);


            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(EditItem.this,ViewItems.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,ViewItems.class);
        startActivity(intent);
    }
}