package com.example.aklny_v30.models;

import android.util.Log;

import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseRestaurantRepository {
    FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance("https://aklny-v3-default-rtdb.firebaseio.com/");
    public DatabaseReference restaurantsRef = databaseInstance.getReference("restaurants");
    public DatabaseReference menusRef = databaseInstance.getReference("menus");
    private RestaurantModel restaurant;
    Task<Void> addRestaurantTask;

//    public Task<DataSnapshot> getRestaurant(String uid)
//    {
//        return usersRef.child(uid).get();
//    }
//
//    public Task<DataSnapshot> userExists(String uid)
//    {
//        return usersRef.child(uid).get();
//    }

    public void setRestaurant(RestaurantModel restaurant) {
        Log.d("PRINT", "Res > " + restaurant.getName());
        String restaurantKey = restaurantsRef.push().getKey();
        String menuKey = menusRef.push().getKey();

        restaurant.setKey(restaurantKey);
        restaurant.setMenuId(menuKey);
        this.restaurant = restaurant;
    }

    public Task<Void> addMenuTask(){
        return menusRef.child(restaurant.getMenuId()).setValue(restaurant.getKey());
    }

    public Task<Void> addRestaurantTask(){
        return restaurantsRef.child(restaurant.getKey()).setValue(restaurant);
    }
}
