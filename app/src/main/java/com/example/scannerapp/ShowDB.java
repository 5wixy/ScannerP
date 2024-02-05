package com.example.scannerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class ShowDB extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_db);
        btnMore.setImageResource(R.drawable.ic_more);



    }

    public void onClick(View view) {


}
}