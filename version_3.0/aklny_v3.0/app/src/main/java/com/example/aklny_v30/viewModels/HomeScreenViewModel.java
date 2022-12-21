package com.example.aklny_v30.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aklny_v30.models.FirebaseRestaurantRepository;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenViewModel extends ViewModel {
    private FirebaseRestaurantRepository fbRestaurantRepository;
    private MutableLiveData<List<RestaurantModel>> restaurantsList;

    public HomeScreenViewModel() {
        fbRestaurantRepository = new FirebaseRestaurantRepository();
        restaurantsList = new MutableLiveData<List<RestaurantModel>>();
    }

    public LiveData<List<RestaurantModel>> getRestaurantsList() {
        Log.d("PRINT", "getRestaurantsList");
        restaurantsList = (MutableLiveData<List<RestaurantModel>>) fbRestaurantRepository.get_liveRestaurantList();
        return restaurantsList;
    }

    public LiveData<List<RestaurantModel>> getFetchedRes() {
        Log.d("PRINT", "getFetchedRes");
        return fbRestaurantRepository.getFetched();
    }

//    private void getRestaurantsFromFirebase(){
//        restaurantsList = fbRestaurantRepository.get_liveRestaurantList();
//    }

    public void listenToDatabase(){
        Log.d("PRINT", "listenToDatabase");
        fbRestaurantRepository.attachPersistentListener();
    }
}
