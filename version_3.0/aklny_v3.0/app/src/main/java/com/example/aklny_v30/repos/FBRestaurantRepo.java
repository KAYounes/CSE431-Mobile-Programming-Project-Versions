package com.example.aklny_v30.repos;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aklny_v30.models.RestaurantModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FBRestaurantRepo {
    public String recentRestaurantKey;
    FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance("https://aklny-v3-default-rtdb.firebaseio.com/");
    public DatabaseReference restaurantsRef = databaseInstance.getReference("restaurants");
    private final MutableLiveData<List<RestaurantModel>> fetched = new MutableLiveData<>();

    public LiveData<List<RestaurantModel>> getFetched() {
        return fetched;
    }

    public Task<Void> addNewRestaurantToFB(RestaurantModel newRestaurant){
        String restaurantKey = restaurantsRef.push().getKey();
        newRestaurant.setKey(restaurantKey);
        recentRestaurantKey = restaurantKey;
        return restaurantsRef.child(restaurantKey).setValue(newRestaurant);
    }


    public void attachPersistentListener(){
        restaurantsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    List<RestaurantModel> fetchedRestaurants = new ArrayList<>();
                    String restaurantKey;
                    RestaurantModel restaurant;

                    for(DataSnapshot data: snapshot.getChildren()){
                        restaurantKey = data.getKey();
                        restaurant = data.getValue(RestaurantModel.class);
                        restaurant.setKey(restaurantKey);

                        fetchedRestaurants.add(restaurant);
                    }

                        fetched.postValue(fetchedRestaurants);}
                catch (Exception e){
                    Log.e("PRINT", "attachPersistentListener > onDataChange > Exception > "
                            + e.getLocalizedMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("PRINT", "FBRestaurantRepo > attachPersistentListener > onCancelled > "
                        + error.getMessage());
            }
        });
    }

    public void filterRestaurants(String filter) {
        restaurantsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    List<RestaurantModel> fetchedRestaurants = new ArrayList<>();
                    String restaurantKey;
                    RestaurantModel restaurant;

                    for(DataSnapshot data: snapshot.getChildren()){
                        restaurantKey = data.getKey();
                        restaurant = data.getValue(RestaurantModel.class);
                        restaurant.setKey(restaurantKey);

                        fetchedRestaurants.add(restaurant);
                    }

                    fetched.postValue(fetchedRestaurants);}
                catch (Exception e){
                    Log.e("PRINT", "attachPersistentListener > onDataChange > Exception > "
                            + e.getLocalizedMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("PRINT", "FBRestaurantRepo > attachPersistentListener > onCancelled > "
                        + error.getMessage());
            }
        });
    }
}
