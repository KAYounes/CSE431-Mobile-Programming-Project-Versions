package com.example.aklny_v30.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aklny_v30.models.CartItemModel;
import com.example.aklny_v30.models.menu_model.MenuItemModel;
import com.example.aklny_v30.models.menu_model.MenuModel;
import com.example.aklny_v30.repos.CartRepo;
import com.example.aklny_v30.repos.FbMenuRepo;

import java.util.List;

public class RestaurantScreenViewModel extends AndroidViewModel {
    private FbMenuRepo fbMenuRepo;
    private CartRepo cartRepo;
    private LiveData<List<MenuModel>> menuList;
    private LiveData<List<CartItemModel>> cart;



    public RestaurantScreenViewModel(Application application) {
        super(application);
        fbMenuRepo = new FbMenuRepo();
        menuList = fbMenuRepo.getFetched();
        cartRepo = new CartRepo(application);
        cart = cartRepo.getCart();
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

    public void addItemToCart(MenuItemModel menuItemModel){
        Log.d("PRINT", "ViewModel > addItemToCart > item > " + menuItemModel.toString());
        CartItemModel cartItem = new CartItemModel(menuItemModel);
        cartRepo.addItem(cartItem);
    }

    public void deleteCart(){
        cartRepo.deleteCart();
    }

    public int getCartItemsCount(){
        int count = 0;
        for(CartItemModel cartItem: cart.getValue()){
            count += cartItem.getQuantity();
        }
        return count;
    }

    public double getCartSubtotal(){
        double subtotal = 0;
        for(CartItemModel cartItem: cart.getValue()){
            subtotal += cartItem.getPrice() * cartItem.getQuantity();
        }

        return subtotal;
    }


    public LiveData<List<CartItemModel>> getCart(){
        return cart;
    }
}
