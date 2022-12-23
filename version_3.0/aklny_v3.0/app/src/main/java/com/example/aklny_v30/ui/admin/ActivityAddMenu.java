package com.example.aklny_v30.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aklny_v30.API.Constants;
import com.example.aklny_v30.models.menu_model.MenuItemModel;
import com.example.aklny_v30.models.menu_model.MenuModel;
import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.ActivityAddMenuBinding;
import com.example.aklny_v30.repos.firebase.FbMenuRepo;
import com.example.aklny_v30.repos.RestaurantRepo;
import com.example.aklny_v30.ui.s5_home_screen.HomeScreenActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityAddMenu extends AppCompatActivity {
    ActivityAddMenuBinding binder;
    RestaurantRepo restaurantRepo;
    FbMenuRepo fbMenuRepo;

    String menuTitle;
    String itemName;
    String itemDescription;
    Double itemPrice;
    String itemThumbnailUrl;

    String generatedMenuKey;
    MenuModel menu;
    List<MenuItemModel> listOfMenuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityAddMenuBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        //

        restaurantRepo = new RestaurantRepo();
        fbMenuRepo = new FbMenuRepo();
        //

        generatedMenuKey = getIntent().getStringExtra(Constants.INTENT_KEY_MENU_KEY);
        binder.menuKey.setText(generatedMenuKey);
        listOfMenuItems = new ArrayList<>();

        binder.btnAddItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    itemName = binder.itemName.getText().toString();
                    itemDescription = binder.itemDescription.getText().toString();
                    itemPrice = Double.parseDouble(binder.itemPrice.getText().toString());
                    itemThumbnailUrl = binder.itemThumbnailUrl.getText().toString();
                }
                catch (Exception exception)
                {
                    displayValidationMessage("Price field is empty");
                }

                if(validateMenuItem())
                {
                    MenuItemModel menuItem = new MenuItemModel(itemName, itemDescription, itemPrice, itemThumbnailUrl);
                    displayValidationMessage("Added " + menuItem.toString());
                    //

                    // Clear edit texts
                    listOfMenuItems.add(menuItem);
                    binder.itemName.setText("");
                    binder.itemDescription.setText("");
                    binder.itemPrice.setText("");
                    binder.itemThumbnailUrl.setText("");
                }
            }
        });
        //


        binder.btnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                menuTitle = binder.menuTitle.getText().toString();

                if(validateMenu())
                {
                    Dialog dialog = new Dialog(ActivityAddMenu.this);
                    dialog.setContentView(R.layout.dialog_logging_in);
                    dialog.setCancelable(false);
                    dialog.show();
                    //

                    menu = new MenuModel(menuTitle, listOfMenuItems);
                    fbMenuRepo.addMenuModelToFB(generatedMenuKey, menu)
                            .addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void unused)
                                {

                                Toast.makeText(ActivityAddMenu.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                                displayValidationMessage("Menu Added");
                                //

                                listOfMenuItems = new ArrayList<>();
                                binder.menuTitle.setText("");
                                dialog.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ActivityAddMenu.this, "Failed to add menu", Toast.LENGTH_SHORT).show();
                                    Log.d("PRINT", "Add Menu> onFailure > " + e.getMessage());
                                    displayValidationMessage(e.getLocalizedMessage());
                                    dialog.dismiss();
                                }
                            });
                }
            }
        });

    }

    private boolean validateMenu(){
        if(!menuTitle.matches(Constants.PATTERN_MENU_TITLE)) {
            displayValidationMessage("Menu name error, please check again.");
            return false;
        }

        if(listOfMenuItems.size() == 0){
            return false;
        }

        return true;
    }
    private boolean validateMenuItem() {
        if(!itemName.matches(Constants.PATTERN_RESTAURANT_NAME)) {
            displayValidationMessage("Item name error, please check again.");
            return false;
        }

        if(!itemDescription.matches(Constants.PATTERN_RESTAURANT_DESCRIPTION)) {
            displayValidationMessage("Description error, please check again.");
            return false;
        }

        if((itemPrice < 0.0) || (itemPrice > 1000.0)) {
            displayValidationMessage("Price error, please check again.");
            return false;
        }

        if(itemThumbnailUrl.isEmpty()) {
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
//        startActivity(new Intent(ActivityAddMenu.this, HomeScreenActivity.class));
        finish();
    }
}