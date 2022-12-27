package com.example.aklny_v30.ui.s9_previous_orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.aklny_v30.API.Constants;
import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.ActivityPreviousOrdersScreenBinding;
import com.example.aklny_v30.models.order_model.OrderModel;
import com.example.aklny_v30.ui.s10_order_screen.Activity_OrderScreen;
import com.example.aklny_v30.ui.ui_utilities.RecyclerViewOnClickListener;
import com.example.aklny_v30.viewModels.VModel_PlaceOrderScreen;
import com.example.aklny_v30.viewModels.VModel_PreviousOrders;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class Activity_PreviousOrdersScreen extends AppCompatActivity implements RecyclerViewOnClickListener {
    ActivityPreviousOrdersScreenBinding binder;
    VModel_PreviousOrders viewModel;
    RCAdapter_PlacedOrdersList rcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityPreviousOrdersScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
    ///////////////////////////////////////////////////////////////////////////////////////////////////
        viewModel = new ViewModelProvider(this).get(VModel_PreviousOrders.class);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        try {
            Log.d("PRINT", "firebaseAuth > " + firebaseAuth);
            Log.d("PRINT", "firebaseUser > " + firebaseUser);
            Log.d("PRINT", "firebaseUser.getUid() > " + firebaseUser.getUid());
            viewModel.attachPersistentListener(FirebaseAuth.getInstance().getCurrentUser().getUid());
        } catch (Exception e) {
            Log.d("PRINT", "Exception > " + e);
        }
//        viewModel.attachPersistentListener(FirebaseAuth.getInstance().getCurrentUser().getUid());
        rcAdapter = new RCAdapter_PlacedOrdersList(new ArrayList<>(), this);
        viewModel.getOrders().observe(this, new Observer<List<OrderModel>>() {
            @Override
            public void onChanged(List<OrderModel> orderModels) {
                Log.d("PRINT", "orderModels > " + orderModels);
                rcAdapter.setData(orderModels);
            }
        });
        binder.previousOrdersList.setAdapter(rcAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binder.previousOrdersList.setLayoutManager(layoutManager);
    }

    @Override
    public void onRecyclerViewClick(int position) {

    }

    @Override
    public void onRecyclerViewClickPayload(Object payload) {
        Intent intent = new Intent(Activity_PreviousOrdersScreen.this, Activity_OrderScreen.class);
        intent.putExtra(Constants.INTENT_KEY_ORDER_KEY, (String) payload);
        startActivity(intent);
    }

    @Override
    public void onRecyclerViewClickPayload(String tag, Object payload) {

    }
}