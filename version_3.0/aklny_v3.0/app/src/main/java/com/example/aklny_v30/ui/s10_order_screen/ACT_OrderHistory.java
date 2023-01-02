package com.example.aklny_v30.ui.s10_order_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.aklny_v30.Constants;
import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.ActivityOrderScreenBinding;
import com.example.aklny_v30.models.OrderModel;
import com.example.aklny_v30.viewModels.VModel_OrderScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ACT_OrderHistory extends AppCompatActivity {
    ActivityOrderScreenBinding binder;
    RVAdapter_OrderItemsList recyclerAdapter;
    VModel_OrderScreen viewModel;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityOrderScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                Log.d("onReceive","Logout in progress");
                finish();
            }
        }, intentFilter);
    ///////////////////////////////////////////////////////////////////////////////////////////////////
        String userKey = FirebaseAuth.getInstance().getUid();
        String orderKey = getIntent().getStringExtra(Constants.INTENT_KEY_ORDER_KEY);
        recyclerAdapter = new RVAdapter_OrderItemsList(new ArrayList<>());
        viewModel = new ViewModelProvider(this).get(VModel_OrderScreen.class);
        viewModel.watchOrder(userKey, orderKey);
        viewModel.getOrder().observe(this, new Observer<OrderModel>() {
            @Override
            public void onChanged(OrderModel orderModel) {
//                Log.d("PRINT", "orderModel > " + orderModel);
//                Log.d("PRINT", "orderModel getCart > " + orderModel.getCart());
                recyclerAdapter.setData(orderModel.getCart());
                updateStatus(orderModel.getOrderStatus());

                Picasso.get().load(orderModel.getRestaurantLogoURL())
                        .placeholder(R.drawable.icon_logo_placeholder_100dp)
                        .error(R.drawable.icon_failed_to_load_logo_100dp)
                        .into(binder.restaurantLogo);

                binder.restaurantName.setText(orderModel.getRestaurantName());
                binder.dateTime.setText(orderModel.getDateOfOrder() + " - " + orderModel.getTimeOfOrder());
                binder.gate.setText("Gate " + orderModel.getDeliveryGate());
                binder.orderSubtotal.setText(orderModel.getSubTotal());
                binder.deliveryFee.setText(orderModel.getDeliveryFee());
                binder.total.setText(orderModel.getTotal());
            }
        });

        binder.orderItemsList.setAdapter(recyclerAdapter);
        binder.orderItemsList.setLayoutManager(new LinearLayoutManager(this));

    }

    private void updateStatus(OrderModel.OrderStatus status) {
        String message = "";
        int icon = R.drawable.icon_timer;
        switch (status) {
            case CANCELLED:
                icon = R.drawable.icon_close_24dp;
                message = "Sorry order was cancelled";
                break;
            case NOT_CONFIRMED:
                message = "Waiting for the restaurant to confirm your order";
                break;
            case PREPARING:
                message = "Your food is being cooked right now!";
                icon = R.drawable.icon_fire;
                break;
            case DELIVERING:
                message = "Your food is on it way to you.";
                icon = R.drawable.icon_truck_24dp;
                break;
            case DELIVERED:
                message = "ORDER RECEIVED";
                icon = R.drawable.icon_tick;
                break;
        }
        binder.statusMessage.setText(message);
        binder.statusIcon.setImageDrawable(getResources().getDrawable(icon));
    }


}