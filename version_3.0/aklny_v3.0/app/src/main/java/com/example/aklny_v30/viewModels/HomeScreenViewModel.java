package com.example.aklny_v30.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.aklny_v30.models.restaurant_model.RestaurantRepo;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;

import java.util.List;

public class HomeScreenViewModel extends ViewModel {
    private RestaurantRepo restaurantRepository;
    private LiveData<List<RestaurantModel>> restaurantsList;

    public HomeScreenViewModel() {
        restaurantRepository = new RestaurantRepo();
        restaurantsList = restaurantRepository.getFetched();
    }


    public LiveData<List<RestaurantModel>> getFetchedRes() {
        Log.d("PRINT", "getFetchedRes");
//        restaurantsList = restaurantRepository.getFetched();
        return restaurantsList;
    }

    public void listenToRestaurantsNodeChanges(){
        Log.d("PRINT", "listenToDatabase");
        restaurantRepository.attachPersistentListener();
    }
}
