package com.example.aklny_v30.ui.s8_order_confirmation_screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.aklny_v30.API.Constants;
import com.example.aklny_v30.API.ResponsesModel;
import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.ActivityOrderConfirmationScreenBinding;
import com.example.aklny_v30.models.cart.CartItemModel;
import com.example.aklny_v30.models.order_model.OrderModel;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.example.aklny_v30.ui.s5_home_screen.Activity_HomeScreen;
import com.example.aklny_v30.viewModels.VModel_PlaceOrderScreen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.List;

public class Activity_PlaceOrderScreen extends AppCompatActivity {
    ActivityOrderConfirmationScreenBinding binder;
    VModel_PlaceOrderScreen viewModel;
    HashMap<String, String> payment;
    RestaurantModel restaurant;
    OrderModel order;
    List<CartItemModel> cart;
    String currentTime;
    String deliveryGate;
///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityOrderConfirmationScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
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
        viewModel.getTimeZoneResponse().observe(this, new Observer<ResponsesModel>() {
            @Override
            public void onChanged(ResponsesModel responsesModel) {
                currentTime = responsesModel.getDateTime();
            }
        });
    ///////////////////////////////////////////////////////////////////////////////////////////////////


        binder.valueTotalAmount.setText(payment.get("total"));
        binder.gateRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int gate = binder.gateRadioGroup.getCheckedRadioButtonId();
                Log.i("PRINT", "gate > " + gate + " | " + i);
                if (gate ==  binder.gate3.getId())
                {
                    Log.i("PRINT", "gate 3");
                    deliveryGate = "3";
                    binder.btnPlaceOrder.setEnabled(true);
                }
                else
                {
                    Log.i("PRINT", "gate 4");
                    deliveryGate = "4";
                    binder.btnPlaceOrder.setEnabled(true);
                }
            }
        });

        binder.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order = new OrderModel();
                order.setSubTotal(payment.get("subtotal"));
                order.setDeliveryFee(payment.get("deliveryFee"));
                order.setDeliveryGate(deliveryGate);
                order.setRestaurantName(restaurant.getName());
                order.setRestaurantLogoURL(restaurant.getLogo());
                order.setTimeOfDelivery("12:00");
                order.setCart(cart);
                order.setTimeOfOrder(currentTime);
                Log.d("PRINT", "Order > " + order.toString());
                Dialog dialog = new Dialog(Activity_PlaceOrderScreen.this);
                dialog.setContentView(R.layout.dialog_logging_in);
                dialog.setCancelable(false);
                dialog.show();
                viewModel.addOrder(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dialog.dismiss();
                        Toast.makeText(Activity_PlaceOrderScreen.this, "Order Placed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Activity_PlaceOrderScreen.this, Activity_HomeScreen.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(Activity_PlaceOrderScreen.this, "Error, Order Was Not Placed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
///////////////////////////////////////////////////////////////////////////////////////////////////


}