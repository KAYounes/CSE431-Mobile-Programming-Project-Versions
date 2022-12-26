package com.example.aklny_v30.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.aklny_v30.models.cart.CartItemModel;
import com.example.aklny_v30.repos.RestaurantRepo;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;

import java.util.List;

public class VModel_HomeScreen extends ViewModel {
    private RestaurantRepo restaurantRepository;
    private LiveData<List<RestaurantModel>> restaurantsList;
    private CartItemModel cart;

    public VModel_HomeScreen() {
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
