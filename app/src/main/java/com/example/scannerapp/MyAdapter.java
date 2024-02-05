package com.example.scannerapp;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>implements Filterable{

    Context context;
    private static ArrayList<ItemModal> itemModalArrayList;
    public FirebaseHandler firebaseHandler;
    private int selectedPos = RecyclerView.NO_POSITION;
    public ArrayList<ItemModal> itemListFilter = new ArrayList<ItemModal>();
    RecyclerView recyclerView;
    private static final String TAG = "MainActivity";

    public MyAdapter(Context context, ArrayList<ItemModal> itemModalArrayList) {
        this.context = context;
        this.itemModalArrayList = itemModalArrayList;
        this.itemListFilter = itemModalArrayList;
        firebaseHandler = new FirebaseHandler();
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_show_db,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        ItemModal item = itemModalArrayList.get(position);
        holder.itemName.setText(item.name);
        holder.itemPrice.setText(item.price);
        holder.itemCode.setText(item.code);
        holder.itemDate.setText(item.date);


        if(!(selectedPos == position)){
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        else{
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }


    }

    @Override
    public int getItemCount() {
        return itemModalArrayList.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if(charSequence == null || charSequence.length() == 0){
                    filterResults.values = itemListFilter;
                    filterResults.count = itemListFilter.size();
                }
                else {
                    String searchStr = charSequence.toString().toLowerCase();
                    ArrayList<ItemModal> itemModels = new ArrayList<ItemModal>();
                    for(ItemModal itemModal : itemListFilter){
                        if(itemModal.getName().toLowerCase().contains(searchStr)){
                            itemModels.add(itemModal);
                        }
                    }
                    filterResults.values = itemModels;
                    filterResults.count = itemModels.size();

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemModalArrayList = (ArrayList<ItemModal>) filterResults.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
    //Filter func for search in Item list activity


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemName, itemPrice, itemCode,itemDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.tvItemName);
            itemPrice = itemView.findViewById(R.id.tvItemPrice);
            itemCode = itemView.findViewById(R.id.tvItemCode);
            itemDate = itemView.findViewById(R.id.tvItemDate);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemView.setBackgroundColor(Color.LTGRAY);
                    Intent i = new Intent(context,EditItem.class);
                    i.putExtra("EDITName", itemName.getText().toString());
                    i.putExtra("EDITPrice", itemPrice.getText().toString());
                    i.putExtra("EDITCode", itemCode.getText().toString());
                    i.putExtra("EDITDate",itemDate.getText().toString());
                    context.startActivity(i);
                    selectedPos = -1;
                    notifyItemChanged(selectedPos);
                    return true;
                }
            });
        }
    }
    public void deleteItem(int pos){
        String code = itemModalArrayList.get(pos).getCode();
        Toast.makeText(context, itemModalArrayList.get(pos).getName() + " Was Deleted", Toast.LENGTH_SHORT).show();
        this.itemModalArrayList.remove(pos);
        //onDelete();
        firebaseHandler.deleteItem(code);
        notifyItemRemoved(pos);
    }


}

