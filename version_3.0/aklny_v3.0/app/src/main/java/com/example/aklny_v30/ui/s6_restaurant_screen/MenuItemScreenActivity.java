package com.example.aklny_v30.ui.s6_restaurant_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.ActivityMenuItemScreenBinding;
import com.example.aklny_v30.models.CartItemModel;
import com.example.aklny_v30.models.menu_model.MenuItemModel;
import com.example.aklny_v30.viewModels.RestaurantScreenViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuItemScreenActivity extends AppCompatActivity {
    ActivityMenuItemScreenBinding binder;
    RestaurantScreenViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityMenuItemScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        viewModel = new ViewModelProvider(this).get(RestaurantScreenViewModel.class);

        MenuItemModel menuItem = getIntent().getParcelableExtra("item");
        binder.itemName.setText(menuItem.getName());
        binder.itemDescription.setText(menuItem.getDescription());
        binder.itemPrice.setText(Double.toString(menuItem.getPrice()));

        Picasso.get().load(menuItem.getThumbnail())
                .placeholder(R.drawable.icon_thumbnail_placeholder)
                .error(R.drawable.icon_failed_to_load_thumbnail)
                .into(binder.itemThumbnail);


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

        binder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("PRINT", "binder.btnAddToCart.setOnClickListener > Btn Clicked");
                viewModel.addItemToCart(menuItem);
            }
        });
    }
}