package com.example.aklny_v30.ui.s8_order_confirmation_screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.aklny_v30.Constants;
import com.example.aklny_v30.models.ResponseModel;
import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.ActivityOrderConfirmationScreenBinding;
import com.example.aklny_v30.models.CartItemModel;
import com.example.aklny_v30.models.OrderModel;
import com.example.aklny_v30.models.RestaurantModel;
import com.example.aklny_v30.ui.s5_home_screen.Activity_HomeScreen;
import com.example.aklny_v30.viewModels.VModel_PlaceOrderScreen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Activity_Checkout extends AppCompatActivity {
    ActivityOrderConfirmationScreenBinding binder;
    VModel_PlaceOrderScreen viewModel;
    HashMap<String, String> payment;
    RestaurantModel restaurant;
    OrderModel order;
    List<CartItemModel> cart;
    String currentTime;
    String currentDate;
    String deliveryGate;
    String timeOfDelivery;
    ResponseModel timeResponse;
    Calendar calendar = Calendar.getInstance();
    BroadcastReceiver receiver;
    private int tries;

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityOrderConfirmationScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                finish();
            }
    }, intentFilter);
    ///////////////////////////////////////////////////////////////////////////////////////////////////

        payment = (HashMap<String, String>) getIntent().getSerializableExtra(Constants.INTENT_KEY_PAYMENT);
        restaurant = getIntent().getParcelableExtra(Constants.INTENT_KEY_RESTAURANT_OBJ);

        order = new OrderModel();
    ///////////////////////////////////////////////////////////////////////////////////////////////////


        viewModel = new ViewModelProvider(this).get(VModel_PlaceOrderScreen.class);
        viewModel.getCart().observe(this, new Observer<List<CartItemModel>>() {
            @Override
            public void onChanged(List<CartItemModel> cartResponse) {
                cart = cartResponse;
            }
        });

        viewModel.getTimeZone();
        viewModel.getTimeZoneResponse().observe(this, new Observer<ResponseModel>() {
            @Override
            public void onChanged(ResponseModel responseModel) {
                if(responseModel != null) {
                    timeResponse = responseModel;
                    currentTime = responseModel.getHourString() + ":" + responseModel.getMinuteString();
                    currentDate = responseModel.getDayString() + "/" + responseModel.getMonthString() + "/" + responseModel.getYear();
                    if (timeResponse.getHour() < Constants.FIRST_PERIOD) {
                        timeOfDelivery = (Constants.FIRST_PERIOD + 2) + ":00";
                        binder.valueDeliveryTime.setText(timeOfDelivery);
                    } else if (timeResponse.getHour() < Constants.SECOND_PERIOD) {
                        timeOfDelivery = (Constants.SECOND_PERIOD + 2) + ":00";
                        binder.valueDeliveryTime.setText(timeOfDelivery);
                    } else {
                        binder.valueDeliveryTime.setText("Tomorrow");
                    }
                }
                else if (tries < 3){
                    tries++;
                    viewModel.getTimeZone();
                }else{
                    currentTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
                    currentDate = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
                    if (calendar.get(Calendar.HOUR_OF_DAY) < Constants.FIRST_PERIOD) {
                        timeOfDelivery = (Constants.FIRST_PERIOD + 2) + ":00";
                        binder.valueDeliveryTime.setText(timeOfDelivery);
                    } else if (calendar.get(Calendar.HOUR_OF_DAY) < Constants.SECOND_PERIOD) {
                        timeOfDelivery = (Constants.SECOND_PERIOD + 2) + ":00";
                        binder.valueDeliveryTime.setText(timeOfDelivery);
                    } else {
                        binder.valueDeliveryTime.setText("Tomorrow");
                    }
                }
            }
        });
    ///////////////////////////////////////////////////////////////////////////////////////////////////
        String userKey = FirebaseAuth.getInstance().getUid();
        Log.d("PRINT", " > " + Constants.SHARED_PREFERENCE_KEY_DEFAULT_GATE + " " + userKey);
        SharedPreferences settings = getSharedPreferences(Constants.SHARED_PREFERENCE_KEY_DEFAULT_GATE + userKey, Context.MODE_PRIVATE);
        int default_gate = settings.getInt(Constants.SHAREDPREFERENCE_PUT_KEY_GATE + userKey, 0);
        if( default_gate == binder.gate3.getId()){
            binder.gate3.setChecked(true);
            deliveryGate = "3";
            binder.btnPlaceOrder.setEnabled(true);
        }
        else if( default_gate == binder.gate4.getId()){
            binder.gate4.setChecked(true);
            deliveryGate = "4";
            binder.btnPlaceOrder.setEnabled(true);
        }

        binder.valueTotalAmount.setText(payment.get("total"));
        binder.gateRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int gate = binder.gateRadioGroup.getCheckedRadioButtonId();

                if (gate ==  binder.gate3.getId())
                {

                    deliveryGate = "3";
                    binder.btnPlaceOrder.setEnabled(true);
                }
                else
                {

                    deliveryGate = "4";

                    binder.btnPlaceOrder.setEnabled(true);
                }
            }
        });

        binder.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (timeResponse == null){
//                    Toast.makeText(Activity_Checkout.this, "We are facing some problems, please try again.", Toast.LENGTH_SHORT).show();
//                    viewModel.getTimeZone();
//                    return;
//                }
//                else if(timeResponse.getHour() >= Constants.SECOND_PERIOD){
//                    Toast.makeText(Activity_Checkout.this, "Sorry, no more orders can be placed right now.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                    order = new OrderModel();
                    order.setSubTotal(payment.get("subtotal"));
                    order.setDeliveryFee(payment.get("deliveryFee"));
                    order.setDeliveryGate(deliveryGate);
                    order.setRestaurantName(restaurant.getName());
                    order.setRestaurantLogoURL(restaurant.getLogo());
                    order.setTimeOfDelivery(timeOfDelivery);
                    order.setCart(cart);


                    order.setTimeOfOrder(currentTime);
                    order.setDateOfOrder(currentDate);


                    Dialog dialog = new Dialog(Activity_Checkout.this);
                    dialog.setContentView(R.layout.dialog_logging_in);
                    dialog.setCancelable(false);
                    dialog.show();
                    viewModel.addOrder(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            dialog.dismiss();
                            Toast.makeText(Activity_Checkout.this, "Order Placed", Toast.LENGTH_SHORT).show();
                            Intent broadcastIntent = new Intent();
                            broadcastIntent.setAction("com.package.ACTION_ORDER_PLACED");
                            sendBroadcast(broadcastIntent);
                            startActivity(new Intent(Activity_Checkout.this, Activity_HomeScreen.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(Activity_Checkout.this, "Error, Order Was Not Placed", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
    }
///////////////////////////////////////////////////////////////////////////////////////////////////


}