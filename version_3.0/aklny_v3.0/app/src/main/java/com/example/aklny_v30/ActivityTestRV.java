package com.example.aklny_v30;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aklny_v30.databinding.ActivityHomeScreenBinding;
import com.example.aklny_v30.databinding.ActivityTestRvBinding;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.example.aklny_v30.ui.FragmentSideBar;
import com.example.aklny_v30.ui.s5_home_screen.RestaurantListRecyclerViewAdapter;
import com.example.aklny_v30.ui.ui_utilities.RecyclerViewOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityTestRV extends AppCompatActivity implements RecyclerViewOnClickListener {
    ActivityTestRvBinding binder;
    RestaurantListRecyclerViewAdapter recyclerViewAdapter;
    List<RestaurantModel> restaurantModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityTestRvBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        populateList();

        recyclerViewAdapter = new RestaurantListRecyclerViewAdapter(restaurantModelList, this);
        binder.restaurantList.setAdapter(recyclerViewAdapter);
        binder.restaurantList.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binder.restaurantList.setNestedScrollingEnabled(false);
        binder.restaurantList.setLayoutManager(layoutManager);
        binder.restaurantList.setNestedScrollingEnabled(false);

        binder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewAdapter.toggleCardTypeDisplay();
            }
        });

        binder.btnOpenSidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(HomeScreenActivity.this, "Click", Toast.LENGTH_SHORT).show();
//                binder.sidebar.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().add(binder.sidebar.getId(),new FragmentSideBar()).commit();
            }
        });

        Log.d("TEXTSIZE", "TEXTSIZE " + binder.textWelcoming.getTextSize());
        Log.d("TEXTSIZE", "TEXTSIZE " + binder.textWelcoming.getTextSize());
//        binder.userFirstName.setTextSize(binder.textWelcoming.getTextSize());
//        Log.d("TEXTSIZE", "TEXTSIZE " + binder.userFirstName.getTextSize());
    }

    private void populateList() {
        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "Texas Buffalo Burger",
                        "Good meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1558983467-5dd0595a81a4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        "https://images.unsplash.com/photo-1504754524776-8f4f37790ca0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80",
                        4.5,
                        15.0)
        );

        restaurantModelList.add(
                new RestaurantModel(
                        "KFC",
                        "Bad meat",
                        "1",
                        "address",
                        "menuId",
                        "https://images.unsplash.com/photo-1516876437184-593fda40c7ce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1172&q=80",
                        "https://images.unsplash.com/photo-1475090169767-40ed8d18f67d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80",
                        4.5,
                        15.0)
        );
    }

    @Override
    public void onRecyclerViewClick(int position) {
//        Toast.makeText(this, "Clicked " + restaurantModelList.get(position).getName(), Toast.LENGTH_SHORT).show();
    }
}