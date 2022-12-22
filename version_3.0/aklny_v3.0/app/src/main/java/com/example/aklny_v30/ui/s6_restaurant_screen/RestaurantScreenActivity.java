package com.example.aklny_v30.ui.s6_restaurant_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.aklny_v30.OuterRecyclerViewAdapter;
import com.example.aklny_v30.MenuItemModel;
import com.example.aklny_v30.MenuModel;
import com.example.aklny_v30.databinding.ActivityRestaurantScreenBinding;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.example.aklny_v30.ui.admin.ActivityAddMenu;
import com.example.aklny_v30.ui.ui_utilities.RecyclerViewOnClickListener;
import com.example.aklny_v30.viewModels.RestaurantScreenViewModel;

import java.util.ArrayList;
import java.util.List;

public class RestaurantScreenActivity extends AppCompatActivity implements RecyclerViewOnClickListener {
    ActivityRestaurantScreenBinding binder;
    List<MenuItemModel> menuItems;
    List<MenuModel> menus;
    RestaurantScreenViewModel viewModel;
    RestaurantModel restaurant;
    OuterRecyclerViewAdapter outerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityRestaurantScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        viewModel = new ViewModelProvider(this).get(RestaurantScreenViewModel.class);
        menuItems = new ArrayList<>();
        menus = new ArrayList<>();

        restaurant = getIntent().getParcelableExtra("RESTAURANT");
        viewModel.listenToMenuNodeChanges(restaurant.getMenuId());
//        MenuItemModel menuItem = new MenuItemModel("Burger", "Good burger", 120.00, "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=999&q=80");
//        menuItems.add(menuItem);
//        menuItem = new MenuItemModel("Chicken", "Good burger", 120.00, "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=999&q=80");
//        menuItems.add(menuItem);
//        MenuModel menu = new MenuModel("Burgers", menuItems);
//        viewModel.addMenu(restaurant.getMenuId(), menu);
        viewModel.getFetchedMenus().observe(this, new Observer<List<MenuModel>>() {
            @Override
            public void onChanged(List<MenuModel> menuModels) {
                Log.d("menu", "onChanged");
                outerAdapter.setMenusList(menuModels);
            }
        });

        List<String> headerFields = new ArrayList<>();
        headerFields.add(restaurant.getThumbnail());
        headerFields.add(restaurant.getName());
        headerFields.add(restaurant.getDescription());
        headerFields.add(restaurant.getAddress());
        headerFields.add(restaurant.getPhoneNumber());
        headerFields.add(restaurant.getDelivery_fee().toString());
        headerFields.add(restaurant.getRating().toString());

//        populate();
        outerAdapter = new OuterRecyclerViewAdapter(menus, RestaurantScreenActivity.this);
        outerAdapter.setHeader(headerFields);
        outerAdapter.addHeader();
        binder.menusList.setAdapter(outerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext()){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        layoutManager.setReverseLayout(false);
        binder.menusList.setLayoutManager(layoutManager);

    }

    private void populate(){
        MenuModel menu;
        MenuItemModel menuItem;
        menuItem = new MenuItemModel("Burger", "Good burger", 120.00, "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=999&q=80");
        menuItems.add(menuItem);
        menuItem = new MenuItemModel("Chicken", "Good Chicken", 120.00, "https://images.unsplash.com/photo-1606755962773-d324e0a13086?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80");
        menuItems.add(menuItem);
        menu = new MenuModel("Good Food", menuItems);
        menus.add(menu);
        menus.add(menu);
        menus.add(menu);
        menus.add(menu);
        menus.add(menu);
        menus.add(menu);


    }

    @Override
    public void onRecyclerViewClick(int position) {
        Intent addMenuIntent = new Intent(RestaurantScreenActivity.this, ActivityAddMenu.class);
        addMenuIntent.putExtra("menu key", restaurant.getMenuId());
        startActivity(addMenuIntent);
        finish();
    }

    @Override
    public void onRecyclerViewClickPayload(MenuItemModel payload) {
        Toast.makeText(this, payload.getName(), Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onRecyclerViewClick(int position) {
////        Toast.makeText(this, , Toast.LENGTH_SHORT).show();
//        Log.d("onCLick", "Activity > " + position);
//
//    }

}