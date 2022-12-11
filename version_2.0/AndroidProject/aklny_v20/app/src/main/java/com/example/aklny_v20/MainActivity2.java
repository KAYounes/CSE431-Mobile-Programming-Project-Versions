package com.example.aklny_v20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements RecyclerViewInterface{
    RecyclerView recyclerView;
    MenuRecyclerViewAdapter recyclerViewAdapter;
    ArrayList<SubmenuModel> submenuModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.menu_recycler_view);

        MenuItemModel item_1 = new MenuItemModel("Chicken Fila","Chicken, Fries", 120.99, R.drawable.logo_2);
        MenuItemModel item_2 = new MenuItemModel("Buffalo","Chicken, Fries", 5.0, R.drawable.logo_3);
        MenuItemModel item_3 = new MenuItemModel("Pizza Hut","Chicken, Pizza", 5.0, R.drawable.logo);

        ArrayList<MenuItemModel> menu_items_1 = new ArrayList<>();
        menu_items_1.add(item_1);
        submenuModels.add(new SubmenuModel(menu_items_1, "Chicken Menu"));


        ArrayList<MenuItemModel> menu_items_2 = new ArrayList<>(menu_items_1);
        menu_items_2.add(item_2);
        submenuModels.add(new SubmenuModel(menu_items_2, "Beef Menu"));

        ArrayList<MenuItemModel> menu_items_3 = new ArrayList<>(menu_items_2);
        menu_items_3.add(item_3);
        submenuModels.add(new SubmenuModel(menu_items_3, "Salad Menu"));

        recyclerViewAdapter = new MenuRecyclerViewAdapter(submenuModels, this);
        Log.d("onCreate", "onCreate: " + recyclerViewAdapter.getItemCount());
        recyclerView.setAdapter(recyclerViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onRecyclerViewClick(int position) {
        Toast.makeText(this, submenuModels.get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }
}