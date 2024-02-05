package com.example.scannerapp;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewItems extends AppCompatActivity implements ItemLongClickListener, View.OnClickListener {

    private static final String SCROLL_POSITION_KEY = "SCROLL_POSITION";
    private LinearLayoutManager layoutManager;
    private int scrollPosition;
    FirebaseHandler handler;
    FirebaseFirestore db;
    // creating variables for our array list,
    // dbHandler, adapter and recycler view.
    private ArrayList<ItemModal> itemModalArrayList;
    private RecyclerView itemsRV;
    MyAdapter  myAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView idtvItemName,idtvItemPrice,idtvItemCode,totalVar,idtvItemDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        // initializing our all variables.
        itemModalArrayList = new ArrayList<ItemModal>();
        totalVar = findViewById(R.id.totalVar);
        idtvItemName = findViewById(R.id.tvItemName);
        idtvItemPrice = findViewById(R.id.tvItemPrice);
        idtvItemCode = findViewById(R.id.tvItemCode);
        idtvItemDate = findViewById(R.id.tvItemDate);
        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                itemModalArrayList.clear();
                myAdapter.notifyDataSetChanged();
                EventChangeListener();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        db = FirebaseFirestore.getInstance();
        itemsRV = findViewById(R.id.idRVItems);
        itemsRV.setHasFixedSize(true);
        itemsRV.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(ViewItems.this,itemModalArrayList);
        itemsRV.setAdapter(myAdapter);
        handler = new FirebaseHandler();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeItem(myAdapter));
        itemTouchHelper.attachToRecyclerView(itemsRV);
        EventChangeListener();

        if (savedInstanceState != null) {
            // Restore the scroll position
            scrollPosition = savedInstanceState.getInt(SCROLL_POSITION_KEY);
            itemsRV.post(() -> layoutManager.scrollToPositionWithOffset(0, scrollPosition));
        }


    }

    private void EventChangeListener() {
        this.db.collection("items").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){

                    Log.e("Firestore error",error.getMessage());
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){
                        itemModalArrayList.add(dc.getDocument().toObject(ItemModal.class));
                    }
                }

                myAdapter.notifyDataSetChanged();

            }
        });
    }


        @Override
    protected void onStart() {
        super.onStart();

    }


    /** search bar */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem manAddItem = menu.findItem(R.id.handAdd);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String searchStr = s;
                myAdapter.getFilter().filter(searchStr);
                return true;
            }

        });
        return super.onCreateOptionsMenu(menu);

    }


    // func that on back press takes back to menu and not to add activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int pos) {
        Toast.makeText(getBaseContext(),itemModalArrayList.get(pos).getName(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.handAdd:
                Intent i = new Intent(this,DBActivity.class);
                this.startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}








