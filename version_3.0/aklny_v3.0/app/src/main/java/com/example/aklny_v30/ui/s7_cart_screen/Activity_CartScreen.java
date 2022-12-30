package com.example.aklny_v30.ui.s7_cart_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.example.aklny_v30.API.Constants;
import com.example.aklny_v30.databinding.ActivityCartScreenBinding;
import com.example.aklny_v30.models.cart.CartItemModel;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.example.aklny_v30.ui.s8_order_confirmation_screen.Activity_PlaceOrderScreen;
import com.example.aklny_v30.ui.ui_utilities.RecyclerViewOnClickListener;
import com.example.aklny_v30.viewModels.VModel_CartScreen;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_CartScreen extends AppCompatActivity implements RecyclerViewOnClickListener {
    ActivityCartScreenBinding binder;
    VModel_CartScreen viewModel;
    RVAdapter_CartItemsList recyclerViewAdapter;
    List<String> footerFields;
    double total;
    HashMap<String, String> payment;
    BroadcastReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityCartScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        intentFilter.addAction("com.package.ACTION_ORDER_PLACED");
        registerReceiver(receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                Log.d("onReceive","Logout in progress");
                finish();
            }
        }, intentFilter);
///////////////////////////////////////////////////////////////////////////////////////////////////

/** Initialize **/
        viewModel = new ViewModelProvider(this).get(VModel_CartScreen.class);
        payment = new HashMap<>();
        payment.put("subtotal", "00.00");
        payment.put("deliveryFee", "00.00");
        payment.put("total", "00.00");
///////////////////////////////////////////////////////////////////////////////////////////////////


/** Retrieve from intent **/
        RestaurantModel restaurant = getIntent().getParcelableExtra(Constants.INTENT_KEY_RESTAURANT_OBJ);
///////////////////////////////////////////////////////////////////////////////////////////////////


        viewModel.getCart().observe(this, new Observer<List<CartItemModel>>() {
            @Override
            public void onChanged(List<CartItemModel> cartItemModels) {
                double subtotal = viewModel.getCartSubtotal();
                double deliveryFee = restaurant.getDeliveryFee();
                double total = subtotal + deliveryFee;

                String subtotalStr = new DecimalFormat("00.00").format(subtotal);
                String deliveryFeeStr = new DecimalFormat("00.00").format(deliveryFee);
                String totalStr = new DecimalFormat("00.00").format(total);
                payment.put("subtotal", subtotalStr);
                payment.put("deliveryFee", deliveryFeeStr);
                payment.put("total", totalStr);

                recyclerViewAdapter.setFooter(payment);
                recyclerViewAdapter.setCartItems(cartItemModels);

                if(cartItemModels.size() == 1){
                    binder.btnCheckout.setEnabled(false);
                }
            }
        });

        binder.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_CartScreen.this, Activity_PlaceOrderScreen.class);
                intent.putExtra(Constants.INTENT_KEY_PAYMENT, payment);
                intent.putExtra(Constants.INTENT_KEY_RESTAURANT_OBJ, restaurant);
                startActivity(intent);
            }
        });

        recyclerViewAdapter = new RVAdapter_CartItemsList(new ArrayList<>(), this);
        recyclerViewAdapter.setFooter(payment);
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
    public void onRecyclerViewClick(int position) {return;}

    @Override
    public void onRecyclerViewClickPayload(Object payload) {return;}

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

//    @Override
//    protected void onDestroy() {
//        if (receiver != null) {
//            unregisterReceiver(receiver);
//            receiver = null;
//        }
//        super.onDestroy();
//    }
}