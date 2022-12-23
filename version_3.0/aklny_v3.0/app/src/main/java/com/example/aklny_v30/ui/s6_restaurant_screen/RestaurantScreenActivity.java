package com.example.aklny_v30.ui.s6_restaurant_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.aklny_v30.models.CartItemModel;
import com.example.aklny_v30.models.menu_model.MenuItemModel;
import com.example.aklny_v30.models.menu_model.MenuModel;
import com.example.aklny_v30.databinding.ActivityRestaurantScreenBinding;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.example.aklny_v30.viewModels.admin.ActivityAddMenu;
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
    MenuRecyclerView menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityRestaurantScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        viewModel = new ViewModelProvider(this).get(RestaurantScreenViewModel.class);
        menuItems = new ArrayList<>();
        menus = new ArrayList<>();


        viewModel.deleteCart();
        Log.d("PRINT", "onCreate Restaurant Screen");

        viewModel.getCart().observe(this, new Observer<List<CartItemModel>>() {
            @Override
            public void onChanged(List<CartItemModel> cart) {
                binder.cartItemCount.setText(String.format("%d", viewModel.getCartItemsCount()));

//                double subtotal = 0;
//                for(CartItemModel cartItem: cart){
//                    subtotal += cartItem.getPrice() * cartItem.getQuantity();
//                }
                binder.cartSubtotal.setText(Double.toString(viewModel.getCartSubtotal()));
            }
        });

        restaurant = getIntent().getParcelableExtra("RESTAURANT");
        viewModel.listenToMenuNodeChanges(restaurant.getMenuId());
        viewModel.getFetchedMenus().observe(this, new Observer<List<MenuModel>>() {
            @Override
            public void onChanged(List<MenuModel> menuModels) {
                Log.d("menu", "onChanged");
                menuAdapter.setMenusList(menuModels);
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

        menuAdapter = new MenuRecyclerView(menus, RestaurantScreenActivity.this);
        menuAdapter.setHeader(headerFields);
        menuAdapter.addHeader();
        binder.menusList.setAdapter(menuAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext()){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        layoutManager.setReverseLayout(false);
        binder.menusList.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.deleteCart();
        Log.d("PRINT", "onDestroy Restaurant Screen");
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
        Toast.makeText(this, payload.getKey(), Toast.LENGTH_SHORT).show();
        Intent goToItemScreen = new Intent(RestaurantScreenActivity.this, MenuItemScreenActivity.class);
        goToItemScreen.putExtra("item", payload);
        startActivity(goToItemScreen);

    }
}