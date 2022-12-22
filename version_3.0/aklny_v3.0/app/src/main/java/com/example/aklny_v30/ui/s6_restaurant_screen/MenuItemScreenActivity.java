package com.example.aklny_v30.ui.s6_restaurant_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.ActivityMenuItemScreenBinding;
import com.example.aklny_v30.models.menu_model.MenuItemModel;
import com.squareup.picasso.Picasso;

public class MenuItemScreenActivity extends AppCompatActivity {
    ActivityMenuItemScreenBinding binder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityMenuItemScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        MenuItemModel menuItem = getIntent().getParcelableExtra("item");
        binder.itemName.setText(menuItem.getName());
        binder.itemDescription.setText(menuItem.getDescription());
        binder.itemPrice.setText(menuItem.getPrice().toString());

        Picasso.get().load(menuItem.getThumbnail())
                .placeholder(R.drawable.icon_thumbnail_placeholder)
                .error(R.drawable.icon_failed_to_load_thumbnail)
                .into(binder.itemThumbnail);
    }
}