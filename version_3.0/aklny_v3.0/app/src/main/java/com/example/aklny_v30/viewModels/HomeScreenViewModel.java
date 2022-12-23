package com.example.aklny_v30.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.aklny_v30.models.CartItemModel;
import com.example.aklny_v30.repos.RestaurantRepo;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;

import java.util.List;

public class HomeScreenViewModel extends ViewModel {
    private RestaurantRepo restaurantRepository;
    private LiveData<List<RestaurantModel>> restaurantsList;
    private CartItemModel cart;

    public HomeScreenViewModel() {
        restaurantRepository = new RestaurantRepo();
        restaurantsList = restaurantRepository.getFetched();
    }


    public LiveData<List<RestaurantModel>> getFetchedRes() {
//        restaurantsList = restaurantRepository.getFetched();
        return restaurantsList;
    }

    public void listenToRestaurantsNodeChanges(){
        restaurantRepository.attachPersistentListener();
    }
}
