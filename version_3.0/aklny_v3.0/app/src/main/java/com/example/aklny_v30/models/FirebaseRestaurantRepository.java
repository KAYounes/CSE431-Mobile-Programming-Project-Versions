package com.example.aklny_v30.models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.example.aklny_v30.models.user_model.UserModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseRestaurantRepository {
    FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance("https://aklny-v3-default-rtdb.firebaseio.com/");
    public DatabaseReference restaurantsRef = databaseInstance.getReference("restaurants");
    public DatabaseReference menusRef = databaseInstance.getReference("menus");
    private RestaurantModel newRestaurant;
    private MutableLiveData<List<RestaurantModel>> _liveRestaurantList = new MutableLiveData<>();
    private MutableLiveData<List<RestaurantModel>> fetched = new MutableLiveData<>();

    public LiveData<List<RestaurantModel>> get_liveRestaurantList() {
        return _liveRestaurantList;
    }

    public LiveData<List<RestaurantModel>> getFetched() {
        return fetched;
    }

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

    public void setNewRestaurant(RestaurantModel newRestaurant) {
        Log.d("PRINT", "Res > " + newRestaurant.getName());
        String restaurantKey = restaurantsRef.push().getKey();
        String menuKey = menusRef.push().getKey();

        newRestaurant.setKey(restaurantKey);
        newRestaurant.setMenuId(menuKey);
        this.newRestaurant = newRestaurant;
    }

    public Task<Void> startMenuTask(){
        return menusRef.child(newRestaurant.getMenuId()).setValue(newRestaurant.getKey());
    }

    public Task<Void> startRestaurantTask(){
        return restaurantsRef.child(newRestaurant.getKey()).setValue(newRestaurant);
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
                        Log.d("PRINT", "data > " + data.getKey());
                        Log.d("PRINT", "data > " + data.getValue(RestaurantModel.class).toString());
                        restaurantKey = data.getKey();
                        restaurant = data.getValue(RestaurantModel.class);
                        restaurant.setKey(restaurantKey);
                        fetchedRestaurants.add(restaurant);
                    }

                        fetched.postValue(fetchedRestaurants);


                    //                    List<RestaurantModel> tempRestaurantsList = new ArrayList<>();

//                    for(DataSnapshot child: snapshot.getChildren()){
//                        tempRestaurantsList.add(child.getValue(RestaurantModel.class));
//                    }
//
//                    _liveRestaurantList = (MutableLiveData<List<RestaurantModel>>) (tempRestaurantsList);
                }
                catch (Exception e){
                    Log.e("PRINT", "Exception > " + e.getLocalizedMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        restaurantsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
