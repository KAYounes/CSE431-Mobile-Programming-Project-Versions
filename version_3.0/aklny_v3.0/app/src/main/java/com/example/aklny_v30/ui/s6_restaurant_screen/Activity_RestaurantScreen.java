package com.example.aklny_v30.ui.s6_restaurant_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aklny_v30.API.Constants;
import com.example.aklny_v30.databinding.ActivityRestaurantScreenBinding;
import com.example.aklny_v30.models.cart.CartItemModel;
import com.example.aklny_v30.models.menu_model.MenuItemModel;
import com.example.aklny_v30.models.menu_model.MenuModel;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.example.aklny_v30.ui.s7_cart_screen.Activity_CartScreen;
import com.example.aklny_v30.ui.admin.ActivityAddMenu;
import com.example.aklny_v30.ui.ui_utilities.RecyclerViewOnClickListener;
import com.example.aklny_v30.viewModels.VModel_RestaurantScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_RestaurantScreen extends AppCompatActivity implements RecyclerViewOnClickListener {
    ActivityRestaurantScreenBinding binder;
    VModel_RestaurantScreen viewModel;
    RestaurantModel restaurant;
    RVAdapter_ListOfMenus menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityRestaurantScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        //

        viewModel = new ViewModelProvider(this).get(VModel_RestaurantScreen.class);
        viewModel.emptyTheCart();
        //

        viewModel.getCart().observe(this, new Observer<List<CartItemModel>>() {
            @Override
            public void onChanged(List<CartItemModel> cart) {
                binder.cartItemCount.setText(String.format("%d", viewModel.getCartItemsCount()));
                binder.cartSubtotal.setText(Double.toString(viewModel.getCartSubtotal()));
            }
        });

        restaurant = getIntent().getParcelableExtra(Constants.INTENT_KEY_RESTAURANT_OBJ);
        viewModel.listenToMenuNodeChanges(restaurant.getMenuId());
        viewModel.getFetchedMenus().observe(this, new Observer<List<MenuModel>>() {
            @Override
            public void onChanged(List<MenuModel> menuModels) {
                Log.d("menu", "onChanged");
                menuAdapter.setMenusList(menuModels);
            }
        });

//        List<String> headerFields = new ArrayList<>();
//        headerFields.add(restaurant.getThumbnail());
//        headerFields.add(restaurant.getName());
//        headerFields.add(restaurant.getDescription());
//        headerFields.add(restaurant.getAddress());
//        headerFields.add(restaurant.getPhoneNumber());
//        headerFields.add(Double.toString(restaurant.getDelivery_fee()));
//        headerFields.add(Double.toString(restaurant.getRating()));

        HashMap<String, String> headerFields = new HashMap<>();
        headerFields.put("Name", restaurant.getName());
        headerFields.put("Description", restaurant.getDescription());
        headerFields.put("Address", restaurant.getAddress());
        headerFields.put("PhoneNumber", restaurant.getPhoneNumber());
        headerFields.put("Thumbnail", restaurant.getThumbnail());
        headerFields.put("DeliveryFee", Double.toString(restaurant.getDeliveryFee()));
        headerFields.put("Rating", Double.toString(restaurant.getRating()));

        menuAdapter = new RVAdapter_ListOfMenus(new ArrayList<>(), Activity_RestaurantScreen.this);
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

        binder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_RestaurantScreen.this, Activity_CartScreen.class);
                intent.putExtra(Constants.INTENT_KEY_RESTAURANT_OBJ, restaurant);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.emptyTheCart();
//        Log.d("PRINT", "onDestroy Restaurant Screen");
    }



    @Override
    public void onRecyclerViewClick(int position) {
        Intent addMenuIntent = new Intent(Activity_RestaurantScreen.this, ActivityAddMenu.class);
        addMenuIntent.putExtra(Constants.INTENT_KEY_MENU_KEY, restaurant.getMenuId());
        startActivity(addMenuIntent);
//        finish();
    }

    @Override
    public void onRecyclerViewClickPayload(Object payload) {
        MenuItemModel item = (MenuItemModel) payload;
        Toast.makeText(this, item.getKey(), Toast.LENGTH_SHORT).show();
        Intent goToItemScreen = new Intent(Activity_RestaurantScreen.this, Activity_MenuItemScreen.class);
        goToItemScreen.putExtra("item", item);
        startActivity(goToItemScreen);

    }

    @Override
    public void onRecyclerViewClickPayload(String tag, Object payload) {

    }
}