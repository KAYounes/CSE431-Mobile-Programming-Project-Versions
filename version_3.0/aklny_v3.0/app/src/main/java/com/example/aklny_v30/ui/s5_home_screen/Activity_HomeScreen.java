package com.example.aklny_v30.ui.s5_home_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.aklny_v30.API.Constants;
import com.example.aklny_v30.databinding.ActivityHomeScreenBinding;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.example.aklny_v30.ui.s6_restaurant_screen.Activity_RestaurantScreen;
import com.example.aklny_v30.ui.ui_utilities.RecyclerViewOnClickListener;
import com.example.aklny_v30.viewModels.VModel_HomeScreen;

import java.util.ArrayList;
import java.util.List;

public class Activity_HomeScreen extends AppCompatActivity implements RecyclerViewOnClickListener {
    ActivityHomeScreenBinding binder;
    RVAdapter_RestaurantListRecycler recyclerViewAdapter;
    List<RestaurantModel> restaurantModelList = new ArrayList<>();
    VModel_HomeScreen viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        viewModel = new ViewModelProvider(this).get(VModel_HomeScreen.class);
        viewModel.listenToRestaurantsNodeChanges();

        binder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewAdapter.toggleCardTypeDisplay();
            }
        });

        binder.btnOpenSidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().add(binder.sidebar.getId(),new Fragment_SideBar()).commit();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerViewAdapter = new RVAdapter_RestaurantListRecycler(restaurantModelList, this);
        viewModel.getFetchedRes().observe(this, new Observer<List<RestaurantModel>>() {
            @Override
            public void onChanged(List<RestaurantModel> restaurantModels) {
                Toast.makeText(Activity_HomeScreen.this, "onChange", Toast.LENGTH_SHORT).show();
                recyclerViewAdapter.setData(restaurantModels);
            }
        });


        binder.restaurantList.setAdapter(recyclerViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setReverseLayout(true);
        binder.restaurantList.setLayoutManager(layoutManager);
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
        Toast.makeText(this, "Clicked " + recyclerViewAdapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Activity_HomeScreen.this, Activity_RestaurantScreen.class);
        intent.putExtra(Constants.INTENT_KEY_RESTAURANT_OBJ, recyclerViewAdapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onRecyclerViewClickPayload(Object payload) {

    }

    @Override
    public void onRecyclerViewClickPayload(String tag, Object payload) {

    }
}