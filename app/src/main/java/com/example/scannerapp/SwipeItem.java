package com.example.scannerapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeItem extends ItemTouchHelper.SimpleCallback {
    MyAdapter itemAdapter;

    SwipeItem(MyAdapter itemAdapter){
        super(0,ItemTouchHelper.LEFT);
        this.itemAdapter = itemAdapter;


    }
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        this.itemAdapter.deleteItem(viewHolder.getAdapterPosition());

    }
}
