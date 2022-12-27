package com.example.aklny_v30.ui.s6_restaurant_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.ActivityMenuItemScreenBinding;
import com.example.aklny_v30.models.cart.CartItemModel;
import com.example.aklny_v30.models.menu_model.MenuItemModel;
import com.example.aklny_v30.viewModels.VModel_MenuItemScreen;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Activity_MenuItemScreen extends AppCompatActivity {
    ActivityMenuItemScreenBinding binder;
    VModel_MenuItemScreen viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binder = ActivityMenuItemScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        viewModel = new ViewModelProvider(this).get(VModel_MenuItemScreen.class);
///////////////////////////////////////////////////////////////////////////////////////////////////



        /** Update view to display current item **/
        MenuItemModel menuItem = getIntent().getParcelableExtra("item");
        binder.itemName.setText(menuItem.getName());
        binder.itemDescription.setText(menuItem.getDescription());
        binder.itemPrice.setText(Double.toString(menuItem.getPrice()));

        Picasso.get().load(menuItem.getThumbnail())
                .placeholder(R.drawable.icon_thumbnail_placeholder)
                .error(R.drawable.icon_failed_to_load_thumbnail)
                .into(binder.itemThumbnail);
///////////////////////////////////////////////////////////////////////////////////////////////////



        /** Update item count, cart count, and cart subtotal **/
        viewModel.getCart().observe(this, new Observer<List<CartItemModel>>()
        {
            @Override
            public void onChanged(List<CartItemModel> cart)
            {
                // Update item current count
                int itemQuantity = viewModel.getItemCountInCart(menuItem.getKey());
                binder.itemCount.setText(String.format("%d", itemQuantity));

                // Update total count of items in cart
                binder.cartItemCount.setText(String.format("%d", viewModel.getCartItemsCount()));

                // Update total count subtotal
                binder.cartSubtotal.setText(Double.toString(viewModel.getCartSubtotal()));
            }
        });

        /** Add item to cart **/
        binder.btnAddToCart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Log.d("PRINT", "binder.btnAddToCart.setOnClickListener > Btn Clicked");
                viewModel.addItemToCart(menuItem);
            }
        });
///////////////////////////////////////////////////////////////////////////////////////////////////
    }
}