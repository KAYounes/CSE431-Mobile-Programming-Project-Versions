package com.example.aklny_v20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{
    RecyclerView restaurantRecyclerView;
    RestaurantRecyclerViewAdapter restaurantRecyclerViewAdapter;
    ArrayList<RestaurantCardModel> restaurantCardModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        restaurantRecyclerView = findViewById(R.id.list_restaurant);

        restaurantCardModels.add(new RestaurantCardModel("Chicken Fila",
                "Chicken, Fries",
                4.8, 15.99, R.drawable.logo, R.drawable.burger_image, RestaurantCardModel.cardType.CARD_SMALL));

        restaurantCardModels.add(new RestaurantCardModel("Buffalo Burger",
                "Burgers, Chicken, Sandwiches, Beef,Burgers, Chicken, Sandwiches, Beef",
                3.8, 15.99, R.drawable.logo_2, R.drawable.burger_image_2, RestaurantCardModel.cardType.CARD_SMALL));

        restaurantCardModels.add(new RestaurantCardModel("Pizza Hut",
                "Pizza, wings",
                5.0, 15.99, R.drawable.logo_3, R.drawable.burger_image_3, RestaurantCardModel.cardType.CARD_SMALL));

        restaurantRecyclerViewAdapter = new RestaurantRecyclerViewAdapter(restaurantCardModels, this);
        restaurantRecyclerView.setAdapter(restaurantRecyclerViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        restaurantRecyclerView.setLayoutManager(layoutManager);

        ImageView button_small_list_view, button_large_list_view;
        button_small_list_view = findViewById(R.id.button_small_list_view);
        button_large_list_view = findViewById(R.id.button_large_list_view);

        button_small_list_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_large_list_view.setBackground(null);
                button_large_list_view.setImageDrawable(getResources().getDrawable(R.drawable.lg_list_icon));
                button_small_list_view.setBackground(getResources().getDrawable(R.drawable.rect_round_left_radius_sml));
                button_small_list_view.setImageDrawable(getResources().getDrawable(R.drawable.sm_list_icon_active));
            }
        });

        button_large_list_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_small_list_view.setBackground(null);
                button_small_list_view.setImageDrawable(getResources().getDrawable(R.drawable.sm_list_icon));
                button_large_list_view.setBackground(getResources().getDrawable(R.drawable.rect_round_right_radius_xs));
                button_large_list_view.setImageDrawable(getResources().getDrawable(R.drawable.lg_list_icon_active));
            }
        });

    }

    @Override
    public void onRecyclerViewClick(int position) {
        Toast.makeText(this, restaurantCardModels.get(position).getName(), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(MainActivity.this, DummyAcivity.class);
//        intent.putExtra("Item", restaurantModels.get(position));
//        startActivity(intent);
    }
}