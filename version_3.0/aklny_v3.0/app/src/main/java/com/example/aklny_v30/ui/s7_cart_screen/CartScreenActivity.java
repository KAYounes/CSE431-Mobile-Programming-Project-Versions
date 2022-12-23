package com.example.aklny_v30.ui.s7_cart_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.aklny_v30.API.Constants;
import com.example.aklny_v30.databinding.ActivityCartScreenBinding;
import com.example.aklny_v30.models.cart.CartItemModel;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.example.aklny_v30.ui.s8_order_confirmation_screen.OrderConfirmationScreenActivity;
import com.example.aklny_v30.ui.ui_utilities.RecyclerViewOnClickListener;
import com.example.aklny_v30.viewModels.CartScreenViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartScreenActivity extends AppCompatActivity implements RecyclerViewOnClickListener {
    ActivityCartScreenBinding binder;
    CartScreenViewModel viewModel;
    CartItemsRecyclerViewAdapter recyclerViewAdapter;
    List<String> footerFields;
    double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityCartScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        viewModel = new ViewModelProvider(this).get(CartScreenViewModel.class);
        RestaurantModel restaurant = getIntent().getParcelableExtra(Constants.INTENT_KEY_RESTAURANT_OBJ);

        footerFields = new ArrayList<>();
        footerFields.add("0.0");
        footerFields.add(Double.toString(restaurant.getDeliveryFee()));
        footerFields.add("0.0");

        viewModel.getCart().observe(this, new Observer<List<CartItemModel>>() {
            @Override
            public void onChanged(List<CartItemModel> cartItemModels) {
                footerFields = new ArrayList<>();
                viewModel.getCartSubtotal();
                footerFields.add(Double.toString(viewModel.getCartSubtotal()));
                footerFields.add(Double.toString(restaurant.getDeliveryFee()));

                total = restaurant.getDeliveryFee() + viewModel.getCartSubtotal();
                footerFields.add(Double.toString(total));

                recyclerViewAdapter.setFooter(footerFields);
                recyclerViewAdapter.setCartItems(cartItemModels);

                if(cartItemModels.size() == 1){
                    binder.btnCheckout.setEnabled(false);
                }
            }
        });

        binder.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartScreenActivity.this, OrderConfirmationScreenActivity.class);
                intent.putExtra(Constants.INTENT_KEY_TOTAL_PRICE, total);
                startActivity(intent);
            }
        });

        binder.cartItems.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter = new CartItemsRecyclerViewAdapter(new ArrayList<>(), this);
        recyclerViewAdapter.setFooter(footerFields);
        recyclerViewAdapter.addFooter();
        binder.cartItems.setAdapter(recyclerViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext()){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        layoutManager.setReverseLayout(true);
        binder.cartItems.setLayoutManager(layoutManager);
    }

    @Override
    public void onRecyclerViewClick(int position) {

    }

    @Override
    public void onRecyclerViewClickPayload(Object payload) {

    }

    @Override
    public void onRecyclerViewClickPayload(String tag, Object payload) {
        switch (tag){
            case Constants.ACTION_REMOVE:
                viewModel.removeItem((String) payload);
                break;
            case Constants.ACTION_DECREMENT:
            case Constants.ACTION_INCREMENT:
                viewModel.updateItem((CartItemModel) payload) ;
                break;
        }
    }
}