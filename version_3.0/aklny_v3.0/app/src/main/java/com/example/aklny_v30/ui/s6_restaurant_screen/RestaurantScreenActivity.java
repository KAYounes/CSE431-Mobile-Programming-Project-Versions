package com.example.aklny_v30.ui.s6_restaurant_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.ActivityAddRestaurantBinding;
import com.example.aklny_v30.databinding.ActivityRestaurantScreenBinding;

public class RestaurantScreenActivity extends AppCompatActivity {
    ActivityRestaurantScreenBinding binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityRestaurantScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
    }
}