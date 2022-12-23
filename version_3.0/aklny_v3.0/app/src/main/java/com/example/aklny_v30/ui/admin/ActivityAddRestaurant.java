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
import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.ActivityAddRestaurantBinding;
import com.example.aklny_v30.repos.firebase.FbMenuRepo;
import com.example.aklny_v30.repos.RestaurantRepo;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.example.aklny_v30.ui.s5_home_screen.HomeScreenActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class ActivityAddRestaurant extends AppCompatActivity {
    ActivityAddRestaurantBinding binder;
    RestaurantRepo restaurantRepo;
    FbMenuRepo fbMenuRepo;

    String name;
    String description;
    String phoneNumber;
    String address;
    String logo;
    String thumbnail;
    double rating;
    double delivery_fee;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binder = ActivityAddRestaurantBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        //

        restaurantRepo = new RestaurantRepo();
        fbMenuRepo = new FbMenuRepo();

        binder.btnAddRestaurant.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    name = binder.restaurantName.getText().toString();
                    description = binder.restaurantDescription.getText().toString();
                    phoneNumber = binder.restaurantPhoneNumber.getText().toString();
                    address = binder.restaurantAddress.getText().toString();
                    logo = binder.restaurantLogoUrl.getText().toString();
                    thumbnail = binder.restaurantThumbnailUrl.getText().toString();
                    rating = Double.parseDouble(binder.restaurantRating.getText().toString());
                    delivery_fee = Double.parseDouble(binder.restaurantDeliveryFee.getText().toString());
                }
                catch (Exception error)
                {
                    displayValidationMessage("A field is empty");
                    return;
                }

                if(validateRestaurant()){
                    Toast.makeText(ActivityAddRestaurant.this, "Adding", Toast.LENGTH_SHORT).show();

                    // Dialog loading show
                    Dialog dialog = new Dialog(ActivityAddRestaurant.this);
                    dialog.setContentView(R.layout.dialog_logging_in);
                    dialog.setCancelable(false);
                    dialog.show();
                    //


                    RestaurantModel restaurant = new RestaurantModel(name, description, phoneNumber,
                            address, logo,thumbnail, rating, delivery_fee);

                    String generatedMenuKey = fbMenuRepo.generateKey();
                    restaurant.setMenuId(generatedMenuKey);
                    restaurantRepo.addNewRestaurantToFB(restaurant)
                            .addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void unused)
                                {
                                    Toast.makeText(ActivityAddRestaurant.this, "Restaurant Added Successfully", Toast.LENGTH_SHORT).show();
                                    displayValidationMessage("Restaurant Added");
                                    dialog.dismiss();

                                    Intent intentAddMenu = new Intent(ActivityAddRestaurant.this, ActivityAddMenu.class);
                                    intentAddMenu.putExtra(Constants.INTENT_KEY_MENU_KEY, generatedMenuKey);
                                    startActivity(intentAddMenu);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Toast.makeText(ActivityAddRestaurant.this, "Failed to add restaurant", Toast.LENGTH_SHORT).show();
                                    Log.d("PRINT", "Add Restaurant > on Failure > " + e.getMessage());
                                    displayValidationMessage(e.getLocalizedMessage());
                                    dialog.dismiss();
                                }
                            });
                }
            }
        });

    }

    private boolean validateRestaurant()
    {
        if(!name.matches(Constants.PATTERN_RESTAURANT_NAME))
        {
            binder.validationMessage.setText("Name error, please check again.");
            return false;
        }

        if(!description.matches(Constants.PATTERN_RESTAURANT_DESCRIPTION))
        {
            binder.validationMessage.setText("description error, please check again.");
            return false;
        }

        if(!phoneNumber.matches(Constants.PATTERN_RESTAURANT_PHONENUMBER))
        {
            binder.validationMessage.setText("phoneNumber error, please check again.");
            return false;
        }

        if(!address.matches(Constants.PATTERN_RESTAURANT_ADDRESS))
        {
            binder.validationMessage.setText("address error, please check again.");
            return false;
        }

        if(logo.isEmpty())
        {
            binder.validationMessage.setText("logo error, please check again.");
            return false;
        }

        if(thumbnail.isEmpty())
        {
            binder.validationMessage.setText("thumbnail error, please check again.");
            return false;
        }

        if((rating < 0.0) || (rating > 5.0))
        {
            binder.validationMessage.setText("rating error, please check again.");
            return false;
        }

        if((delivery_fee < 0.0) || (delivery_fee > 100.0))
        {
            binder.validationMessage.setText("delivery_fee error, please check again.");
            return false;
        }

        return true;
    }

    private void displayValidationMessage(String message){
        binder.validationMessage.setText(message);
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(ActivityAddRestaurant.this, HomeScreenActivity.class));
    }
}