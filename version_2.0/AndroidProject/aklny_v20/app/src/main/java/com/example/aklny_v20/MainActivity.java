package com.example.aklny_v20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{
    RecyclerView restaurantRecyclerView;
    RestaurantRecyclerViewAdapter restaurantRecyclerViewAdapter;
    ArrayList<RestaurantModel> restaurantModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        restaurantRecyclerView = findViewById(R.id.list_restaurant);

        restaurantModels.add(new RestaurantModel("Buffalo Burger",
                "Burgers, Chicken, Sandwiches, Beef,Burgers, Chicken, Sandwiches, Beef",
                3.8, 15.99, R.drawable.teksas_buffalo_burger_logo));
//

        restaurantModels.add(new RestaurantModel("Chicken Fila",
                "Chicken, Fries",
                4.8, 15.99, R.drawable.logo));
//

        restaurantModels.add(new RestaurantModel("Pizza Hut",
                "Pizza, wings",
                5.0, 15.99, R.drawable.logo_3));

        restaurantRecyclerViewAdapter = new RestaurantRecyclerViewAdapter(restaurantModels, this);
        restaurantRecyclerView.setAdapter(restaurantRecyclerViewAdapter);
//
        restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    @Override
    public void onRecyclerViewClick(int position) {
        Toast.makeText(this, restaurantModels.get(position).getName(), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(MainActivity.this, DummyAcivity.class);
//        intent.putExtra("Item", restaurantModels.get(position));
//        startActivity(intent);
    }
}