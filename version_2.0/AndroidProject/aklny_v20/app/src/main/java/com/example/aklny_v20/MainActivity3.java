package com.example.aklny_v20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity implements RecyclerViewInterface{
    RecyclerView recyclerView;
    MenuItemRecyclerViewAdapter recyclerViewAdapter;
    ArrayList<MenuItemChoiceModel> choices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        recyclerView = findViewById(R.id.menu_item_builder_recycler_view);

        MenuItemChoiceValueModel size_small = new MenuItemChoiceValueModel("Small", 10.00);
        MenuItemChoiceValueModel size_medium = new MenuItemChoiceValueModel("Medium", 20.00);
        MenuItemChoiceValueModel size_large = new MenuItemChoiceValueModel("Large", 30.00);
        ArrayList<MenuItemChoiceValueModel> size_values = new ArrayList<>();
        size_values.add(size_small);
        size_values.add(size_medium);
        size_values.add(size_large);

        MenuItemChoiceValueModel extras_sauce = new MenuItemChoiceValueModel("Sauce", 10.00);
        MenuItemChoiceValueModel extras_salad = new MenuItemChoiceValueModel("Salad", 20.00);
        MenuItemChoiceValueModel extras_desert = new MenuItemChoiceValueModel("Desert", 30.00);
        ArrayList<MenuItemChoiceValueModel> extras_values = new ArrayList<>();
        extras_values.add(extras_sauce);
        extras_values.add(extras_salad);
        extras_values.add(extras_desert);

        MenuItemChoiceValueModel beverage_water = new MenuItemChoiceValueModel("Water", 10.00);
        MenuItemChoiceValueModel beverage_orange_juice = new MenuItemChoiceValueModel("Orange Juice", 20.00);
        MenuItemChoiceValueModel beverage_pepsi = new MenuItemChoiceValueModel("Pepsi", 30.00);
        ArrayList<MenuItemChoiceValueModel> beverage_values = new ArrayList<>();
        beverage_values.add(beverage_water);
        beverage_values.add(beverage_orange_juice);
        beverage_values.add(beverage_pepsi);

        MenuItemChoiceModel size = new MenuItemChoiceModel("Size", size_values, MenuItemChoiceModel.choiceType.SINGLE_VALUE);
        MenuItemChoiceModel extras = new MenuItemChoiceModel("Extras", extras_values, MenuItemChoiceModel.choiceType.MULTI_CHOICE);
        MenuItemChoiceModel beverage = new MenuItemChoiceModel("Beverage", beverage_values, MenuItemChoiceModel.choiceType.SINGLE_VALUE);

        ArrayList<MenuItemChoiceModel> choices = new ArrayList<>();
        choices.add(size);
        choices.add(extras);
        choices.add(beverage);
        choices.get(0);


        recyclerViewAdapter = new MenuItemRecyclerViewAdapter(choices, this);

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
        Log.d("PRINT", "getItemCount: " + recyclerViewAdapter.getItemCount());
        Log.d("PRINT", "position: " + position);
        Log.d("PRINT", "choices: " + choices.size());
        Toast.makeText(this, choices.get(position).getChoice_name(), Toast.LENGTH_SHORT).show();
    }
}