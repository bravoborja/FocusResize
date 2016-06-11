package com.example.borjabravo.focusresize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.library.FocusResizeScrollListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        createCustomAdapter(recyclerView, linearLayoutManager);
    }

    private void createDefaultAdapter(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        DefaultAdapter defaultAdapter = new DefaultAdapter(this, (int) getResources().getDimension(R.dimen.custom_item_height));
        defaultAdapter.addItems(addItems());
        if (recyclerView != null) {
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(defaultAdapter);
            recyclerView.addOnScrollListener(new FocusResizeScrollListener<>(defaultAdapter, linearLayoutManager));
        }
    }

    private void createCustomAdapter(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        CustomAdapter customAdapter = new CustomAdapter(this, (int) getResources().getDimension(R.dimen.custom_item_height));
        customAdapter.addItems(addItems());
        if (recyclerView != null) {
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(customAdapter);
            recyclerView.addOnScrollListener(new FocusResizeScrollListener<>(customAdapter, linearLayoutManager));
        }
    }

    private List<CustomObject> addItems() {
        List<CustomObject> items = new ArrayList<>();
        items.add(new CustomObject("Possibility", "The Hill", R.drawable.image01));
        items.add(new CustomObject("Finishing", "The Grid", R.drawable.image02));
        items.add(new CustomObject("Craftsmanship", "Metropolitan Center", R.drawable.image03));
        items.add(new CustomObject("Opportunity", "The Hill", R.drawable.image04));
        items.add(new CustomObject("Starting Over", "The Grid", R.drawable.image05));
        items.add(new CustomObject("Identity", "Metropolitan Center", R.drawable.image06));
        return items;
    }
}