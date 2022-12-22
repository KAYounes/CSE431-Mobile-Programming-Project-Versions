package com.example.aklny_v30.models.restaurant_model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRepo {
    FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance("https://aklny-v3-default-rtdb.firebaseio.com/");
    public DatabaseReference restaurantsRef = databaseInstance.getReference("restaurants");
    private MutableLiveData<List<RestaurantModel>> fetched = new MutableLiveData<>();
    public String recentRestaurantKey;

    public LiveData<List<RestaurantModel>> getFetched() {
        return fetched;
    }

    public Task<Void> addNewRestaurantToFbase(RestaurantModel newRestaurant){
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
//                    Log.d("PRINT", "getChildrenCount > " + snapshot.getChildrenCount());
//                    Log.d("PRINT", "getKey > " + snapshot.getKey());
//                    Log.d("PRINT", "toString > " + snapshot.toString());
//                    Log.d("PRINT", "getValue().toString() > " + snapshot.getChildren());
//                    Log.d("PRINT", "getValue().toString() > " + ((Map<String, Object>) snapshot.getValue()).toString());
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
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
