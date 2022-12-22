package com.example.aklny_v30.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.aklny_v30.MenuModel;
import com.example.aklny_v30.models.databases.FbMenuRepo;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.example.aklny_v30.models.restaurant_model.RestaurantRepo;

import java.util.List;

public class RestaurantScreenViewModel extends ViewModel {
    private FbMenuRepo fbMenuRepo;
    private LiveData<List<MenuModel>> menuList;

    public RestaurantScreenViewModel() {
        fbMenuRepo = new FbMenuRepo();
        menuList = fbMenuRepo.getFetched();
    }

    public void addMenu(String key, MenuModel menu){
        fbMenuRepo.addMenuToFbase(key, menu);
    }


    public LiveData<List<MenuModel>> getFetchedMenus() {
        Log.d("menu", "getFetchedMenus");
        return menuList;
    }

    public void listenToMenuNodeChanges(String menuKey){
        Log.d("menu", "listenToDatabase");
        fbMenuRepo.attachPersistentListener(menuKey);
    }
}
