package com.example.aklny_v30.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aklny_v30.MenuItemModel;
import com.example.aklny_v30.MenuModel;
import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.ActivityAddMenuBinding;
import com.example.aklny_v30.models.databases.FbMenuRepo;
import com.example.aklny_v30.models.restaurant_model.RestaurantRepo;
import com.example.aklny_v30.ui.s5_home_screen.HomeScreenActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityAddMenu extends AppCompatActivity {
    ActivityAddMenuBinding binder;
    RestaurantRepo restaurantRepo;
    FbMenuRepo fbMenuRepo;
    String menuName;
    String itemName;
    String itemDescription;
    Double itemPrice;
    String itemThumbnailUrl;
    List<MenuItemModel> menuItems;
    MenuModel menu;
    String menuKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityAddMenuBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        restaurantRepo = new RestaurantRepo();
        fbMenuRepo = new FbMenuRepo();
        menuKey = getIntent().getStringExtra("menu key");
        menuItems = new ArrayList<>();

        binder.btnAddItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                itemName = binder.itemName.getText().toString();
                itemDescription = binder.itemDescription.getText().toString();
                itemPrice = Double.parseDouble(binder.itemPrice.getText().toString());
                itemThumbnailUrl = binder.itemThumbnailUrl.getText().toString();

                if(validateMenuItem())
                {
                    Toast.makeText(ActivityAddMenu.this, "Added", Toast.LENGTH_SHORT).show();
                    MenuItemModel menuItem = new MenuItemModel(itemName, itemDescription, itemPrice, itemThumbnailUrl);
                    menuItems.add(menuItem);
                    binder.itemName.setText("");
                    binder.itemDescription.setText("");
                    binder.itemPrice.setText("");
                    binder.itemThumbnailUrl.setText("");
                }
            }
        });

        binder.btnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                menuName = binder.menuName.getText().toString();

                if(validateMenu())
                {
                    Dialog dialog = new Dialog(ActivityAddMenu.this);
                    dialog.setContentView(R.layout.dialog_logging_in);
                    dialog.setCancelable(false);
                    dialog.show();
                    String menuId;
                    menu = new MenuModel(menuName, menuItems);

                    fbMenuRepo.addMenuToFbase(menuKey, menu)
                            .addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void unused)
                                {

                                Toast.makeText(ActivityAddMenu.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                                displayValidationMessage("Restaurant Added");
                                menuItems = new ArrayList<>();
                                binder.menuName.setText("");
                                dialog.dismiss();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ActivityAddMenu.this, "Failed to add menu", Toast.LENGTH_SHORT).show();
                                    Log.d("PRINT", e.getLocalizedMessage());
                                    displayValidationMessage(e.getLocalizedMessage());
                                    dialog.dismiss();
                                }
                            });
                }
            }
        });

    }

    private boolean validateMenu(){
        if(!menuName.matches("^[a-zA-z ]{3,60}$")) {
//            Toast.makeText(this, "name", Toast.LENGTH_SHORT).show();
            displayValidationMessage("Menu name error, please check again.");
            return false;
        }

        if(menuItems.size() == 0){
            return false;
        }

        return true;
    }
    private boolean validateMenuItem() {
        if(!itemName.matches("^[a-zA-z ]{3,30}$")) {
//            Toast.makeText(this, itemName, Toast.LENGTH_SHORT).show();
            displayValidationMessage("Item name error, please check again.");
            return false;
        }

        if(!itemDescription.matches("^[a-zA-z0-9 ,.!-]{3,100}$")) {
            Toast.makeText(this, itemDescription, Toast.LENGTH_SHORT).show();
            displayValidationMessage("Description error, please check again.");
            return false;
        }

        if((itemPrice < 0.0) && (itemPrice > 1000.0)) {
//            Toast.makeText(this, "rating", Toast.LENGTH_SHORT).show();
            displayValidationMessage("Price error, please check again.");
            return false;
        }

        if(itemThumbnailUrl.isEmpty()) {
//            Toast.makeText(this, "logo", Toast.LENGTH_SHORT).show();
            displayValidationMessage("Thumbnail Url error, please check again.");
            return false;
        }

        return true;
    }

    private void displayValidationMessage(String message){
        binder.validationMessage.setText(message);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActivityAddMenu.this, HomeScreenActivity.class));
        finish();
    }
}