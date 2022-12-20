package com.example.aklny_v30;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.aklny_v30.databinding.ActivityAddRestaurantBinding;
import com.example.aklny_v30.models.FirebaseRestaurantRepository;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.example.aklny_v30.ui.s5_home_screen.HomeScreenActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class ActivityAddRestaurant extends AppCompatActivity {
    ActivityAddRestaurantBinding binder;
    FirebaseRestaurantRepository restaurantRepository;
    String name;
    String description;
    String phoneNumber;
    String address;
    String menuId;
    String logo;
    String thumbnail;
    double rating;
    double delivery_fee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityAddRestaurantBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        restaurantRepository = new FirebaseRestaurantRepository();

        binder.btnAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = binder.restaurantName.getText().toString();
                description = binder.restaurantDescription.getText().toString();
                phoneNumber = binder.restaurantPhoneNumber.getText().toString();
                address = binder.restaurantAddress.getText().toString();
                logo = binder.restaurantLogoUrl.getText().toString();
                thumbnail = binder.restaurantThumbnailUrl.getText().toString();
                rating = Double.parseDouble(binder.restaurantRating.getText().toString());
                delivery_fee = Double.parseDouble(binder.restaurantDeliveryFee.getText().toString());

                if(validateRestaurant()){
                    Toast.makeText(ActivityAddRestaurant.this, "Adding", Toast.LENGTH_SHORT).show();
                    Dialog dialog = new Dialog(ActivityAddRestaurant.this);
                    dialog.setContentView(R.layout.dialog_logging_in);
                    dialog.setCancelable(false);
                    dialog.show();
                    RestaurantModel newRestaurant =
                            new RestaurantModel(
                                    name,
                                    description,
                                    phoneNumber,
                                    address,
                                    logo,
                                    thumbnail,
                                    rating,
                                    delivery_fee
                            );

                    restaurantRepository.setRestaurant(newRestaurant);
                    restaurantRepository.addMenuTask()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            restaurantRepository.addRestaurantTask()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                                Toast.makeText(ActivityAddRestaurant.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                                            displayValidationMessage("Restaurant Added");
                                            dialog.dismiss();
                                        }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ActivityAddRestaurant.this, "Added Failed, see logs", Toast.LENGTH_SHORT).show();
                                            Log.d("PRINT", e.getLocalizedMessage());
                                            displayValidationMessage(e.getLocalizedMessage());
                                            dialog.dismiss();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ActivityAddRestaurant.this, "Added Failed, see logs", Toast.LENGTH_SHORT).show();
                            Log.d("PRINT", e.getLocalizedMessage());
                            displayValidationMessage(e.getLocalizedMessage());
                            dialog.dismiss();
                        }
                    });
//                    try {
//
//                        restaurantRepository.addNewRestaurant(newRestaurant).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        Toast.makeText(ActivityAddRestaurant.this, "Added Successfully", Toast.LENGTH_SHORT).show();
//                                        dialog.dismiss();
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(ActivityAddRestaurant.this, "Added Failed", Toast.LENGTH_SHORT).show();
//                                        dialog.dismiss();
//                                    }
//                                });
//                    }
//                    catch (Exception e)
//                    {
//                        Log.d("PRINT", "Error " + e.getLocalizedMessage());
//                    }
                }
            }
        });

    }

    private boolean validateRestaurant() {
        if(name.matches("^[a-zA-z]{3,30}$")) {
            Toast.makeText(this, "name", Toast.LENGTH_SHORT).show();
            binder.validationMessage.setText("name error, please check again.");
            return false;
        }

        if(description.matches("^[a-zA-z]{10,60}$")) {
            Toast.makeText(this, "description", Toast.LENGTH_SHORT).show();
            binder.validationMessage.setText("description error, please check again.");
            return false;
        }

        if(!phoneNumber.matches("^\\+201[0-9]{9}$")) {
            Toast.makeText(this, "phoneNumber", Toast.LENGTH_SHORT).show();
            binder.validationMessage.setText("phoneNumber error, please check again.");
            return false;
        }

        if(!address.matches("^.{10,90}$")) {
            Toast.makeText(this, "address", Toast.LENGTH_SHORT).show();
            binder.validationMessage.setText("address error, please check again.");
            return false;
        }

        if(logo.isEmpty()) {
            Toast.makeText(this, "logo", Toast.LENGTH_SHORT).show();
            binder.validationMessage.setText("logo error, please check again.");
            return false;
        }

        if(thumbnail.isEmpty()) {
            Toast.makeText(this, "thumbnail", Toast.LENGTH_SHORT).show();
            binder.validationMessage.setText("thumbnail error, please check again.");
            return false;
        }

        if((rating < 0.0) && (rating > 5.0)) {
            Toast.makeText(this, "rating", Toast.LENGTH_SHORT).show();
            binder.validationMessage.setText("rating error, please check again.");
            return false;
        }

        if((delivery_fee < 0.0) && (delivery_fee > 100.0)) {
            Toast.makeText(this, "delivery_fee", Toast.LENGTH_SHORT).show();
            binder.validationMessage.setText("delivery_fee error, please check again.");
            return false;
        }

        return true;
    }

    private void displayValidationMessage(String message){
        binder.validationMessage.setText(message);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ActivityAddRestaurant.this, HomeScreenActivity.class));
        finish();
    }
}