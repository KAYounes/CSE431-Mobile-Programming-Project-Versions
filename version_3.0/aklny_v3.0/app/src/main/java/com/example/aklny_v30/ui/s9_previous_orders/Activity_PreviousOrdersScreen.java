package com.example.aklny_v30.ui.s9_previous_orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.aklny_v30.API.Constants;
import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.ActivityPreviousOrdersScreenBinding;
import com.example.aklny_v30.models.order_model.OrderModel;
import com.example.aklny_v30.models.user_model.UserModel;
import com.example.aklny_v30.ui.s10_order_screen.Activity_OrderScreen;
import com.example.aklny_v30.ui.s5_home_screen.Fragment_SideBar;
import com.example.aklny_v30.ui.ui_utilities.RecyclerViewOnClickListener;
import com.example.aklny_v30.viewModels.VModel_PlaceOrderScreen;
import com.example.aklny_v30.viewModels.VModel_PreviousOrders;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Activity_PreviousOrdersScreen extends AppCompatActivity implements RecyclerViewOnClickListener {
    ActivityPreviousOrdersScreenBinding binder;
    VModel_PreviousOrders viewModel;
    RCAdapter_PlacedOrdersList rcAdapter;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityPreviousOrdersScreenBinding.inflate(getLayoutInflater());
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
        viewModel = new ViewModelProvider(this).get(VModel_PreviousOrders.class);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        try {
            viewModel.attachPersistentListener(FirebaseAuth.getInstance().getCurrentUser().getUid());
        } catch (Exception e) {
            Log.e("PRINT", "Exception > " + e);
        }
//        viewModel.attachPersistentListener(FirebaseAuth.getInstance().getCurrentUser().getUid());
        rcAdapter = new RCAdapter_PlacedOrdersList(new ArrayList<>(), this);
        viewModel.getOrders().observe(this, new Observer<List<OrderModel>>() {
            @Override
            public void onChanged(List<OrderModel> orderModels) {
//                Log.d("PRINT", "orderModels > " + orderModels);
                rcAdapter.setData(orderModels);
            }
        });
        binder.previousOrdersList.setAdapter(rcAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        binder.previousOrdersList.setLayoutManager(layoutManager);

        binder.btnOpenSidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().add(binder.sidebar.getId(),new Fragment_SideBar()).commit();
            }
        });

        viewModel.getUser().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel user) {
                Picasso.get().load(user.getPhotoURL())
                        .placeholder(R.drawable.icon_profile_placeholder)
//                    .error(R.drawable.icon_failed_to_load_thumbnail)
                        .into(binder.imageProfile);
            }
        });
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