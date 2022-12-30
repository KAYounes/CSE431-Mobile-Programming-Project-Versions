package com.example.aklny_v30.ui.s5_home_screen;

import static android.content.Intent.ACTION_TIME_TICK;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aklny_v30.API.Constants;
import com.example.aklny_v30.API.ResponsesModel;
import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.ActivityHomeScreenBinding;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.example.aklny_v30.models.user_model.UserModel;
import com.example.aklny_v30.ui.Activity_ProfileScreen;
import com.example.aklny_v30.ui.s6_restaurant_screen.Activity_RestaurantScreen;
import com.example.aklny_v30.ui.ui_utilities.RecyclerViewOnClickListener;
import com.example.aklny_v30.viewModels.VModel_HomeScreen;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Activity_HomeScreen extends AppCompatActivity implements RecyclerViewOnClickListener {
    ActivityHomeScreenBinding binder;
    RVAdapter_RestaurantListRecycler recyclerViewAdapter;
    List<RestaurantModel> restaurantModelList = new ArrayList<>();
    VModel_HomeScreen viewModel;
    Handler handler;
    Runnable runnable;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityHomeScreenBinding.inflate(getLayoutInflater());
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

        viewModel = new ViewModelProvider(this).get(VModel_HomeScreen.class);
        viewModel.watchRestaurants();
        viewModel.getTimeZone();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_TIME_TICK);

        viewModel.watchRemoteUser();
        viewModel.getRemoteUser().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                viewModel.updateLocalUser(userModel);
            }
        });

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //do something based on the intent's action
//                Toast.makeText(Activity_HomeScreen.this, "1 Minute Passed", Toast.LENGTH_SHORT).show();
                viewModel.getTimeZone();
            }
        };
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(Activity_HomeScreen.this, "RUNNING", Toast.LENGTH_SHORT).show();
                viewModel.getTimeZone();
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);
        binder.textInputEditSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                recyclerViewAdapter.filter(charSequence.toString().toLowerCase().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                viewModel.filterRestaurants(editable.toString());
//                recyclerViewAdapter.filter(editable.toString());
            }
        });

        registerReceiver(receiver, filter);

        binder.serverDown.setVisibility(View.VISIBLE);
        binder.updates.setVisibility(View.GONE);
        binder.ordersTomorrow.setVisibility(View.GONE);
        viewModel.getApiResponse().observe(this, new Observer<ResponsesModel>() {
            @Override
            public void onChanged(ResponsesModel response) {
                if(response == null){
                    binder.serverDown.setVisibility(View.VISIBLE);
                    binder.updates.setVisibility(View.GONE);
                    binder.ordersTomorrow.setVisibility(View.GONE);
                }
                else {
//                    Toast.makeText(Activity_HomeScreen.this, "REMOVE", Toast.LENGTH_SHORT).show();
                    handler.removeCallbacks(runnable);
//                    Log.d("PRINT", "deliveryTIme > " + response.getHour());
                    if (response.getHour() < Constants.FIRST_PERIOD) {
                        binder.deliverTime.setText(Constants.FIRST_PERIOD + ":00");
                        binder.updates.setVisibility(View.VISIBLE);
                        binder.serverDown.setVisibility(View.GONE);
                        binder.ordersTomorrow.setVisibility(View.GONE);
                        String timeLeft = viewModel.getTimeUntil10(response.getHour(), response.getMinute());
                        binder.timeLeft.setText(timeLeft);
                    } else if (response.getHour() < Constants.SECOND_PERIOD) {
                        binder.deliverTime.setText(Constants .SECOND_PERIOD+ ":00");
                        binder.updates.setVisibility(View.VISIBLE);
                        binder.serverDown.setVisibility(View.GONE);
                        binder.ordersTomorrow.setVisibility(View.GONE);
                        String timeLeft = viewModel.getTimeUntil13(response.getHour(), response.getMinute());
                        binder.timeLeft.setText(timeLeft);
                    } else {
                        binder.updates.setVisibility(View.GONE);
                        binder.serverDown.setVisibility(View.GONE);
                        binder.ordersTomorrow.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        binder.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_HomeScreen.this, Activity_ProfileScreen.class));
            }
        });

        binder.btnSmallList.setEnabled(false);
        binder.btnLargeList.setEnabled(true);
        binder.btnSmallList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewAdapter.smallList();
                binder.btnSmallList.setEnabled(false);
                binder.btnLargeList.setEnabled(true);
            }
        });

        binder.btnLargeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewAdapter.largeList();
                binder.btnSmallList.setEnabled(true);
                binder.btnLargeList.setEnabled(false);
            }
        });

        binder.btnOpenSidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().add(binder.sidebar.getId(),new Fragment_SideBar()).commit();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel.getUser().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel user) {
                if(user == null){
//                    Log.d("PRINT", "user is null > ");
                    return;
                }

//                Log.d("PRINT", "User is not null > " + user);
                Picasso.get().load(user.getPhotoURL())
                        .placeholder(R.drawable.icon_profile_placeholder)
//                    .error(R.drawable.icon_failed_to_load_thumbnail)
                        .into(binder.imageProfile);

                binder.userFirstName.setText(user.getFirstName());
            }
        });



        recyclerViewAdapter = new RVAdapter_RestaurantListRecycler(restaurantModelList, this);
        viewModel.getFetchedRes().observe(this, new Observer<List<RestaurantModel>>() {
            @Override
            public void onChanged(List<RestaurantModel> restaurantModels) {
//                Toast.makeText(Activity_HomeScreen.this, "onChange", Toast.LENGTH_SHORT).show();
                recyclerViewAdapter.setData(restaurantModels);
            }
        });


        binder.restaurantList.setAdapter(recyclerViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setReverseLayout(true);
        binder.restaurantList.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }

    @Override
    public void onRecyclerViewClick(int position) {
//        Toast.makeText(this, "Clicked " + recyclerViewAdapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Activity_HomeScreen.this, Activity_RestaurantScreen.class);
        intent.putExtra(Constants.INTENT_KEY_RESTAURANT_OBJ, recyclerViewAdapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onRecyclerViewClickPayload(Object payload) {

    }

    @Override
    public void onRecyclerViewClickPayload(String tag, Object payload) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof TextInputEditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
}