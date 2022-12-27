package com.example.aklny_v30.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aklny_v30.models.cart.CartItemModel;
import com.example.aklny_v30.models.menu_model.MenuModel;
import com.example.aklny_v30.repos.CartRepo;
import com.example.aklny_v30.repos.firebase.FbMenuRepo;

import java.util.List;

public class VModel_RestaurantScreen extends AndroidViewModel
{
    private FbMenuRepo fbMenuRepo;
    private CartRepo cartRepo;
    private LiveData<List<MenuModel>> menuList;
    private LiveData<List<CartItemModel>> cart;

    public VModel_RestaurantScreen(Application application)
    {
        super(application);
        fbMenuRepo = new FbMenuRepo();
        menuList = fbMenuRepo.getMenusLive();
        cartRepo = new CartRepo(application);
        cart = cartRepo.getCart();
    }

    public LiveData<List<MenuModel>> getFetchedMenus()
    {
        Log.d("menu", "getFetchedMenus");
        return menuList;
    }

    public void listenToMenuNodeChanges(String menuKey)
    {
        Log.d("menu", "listenToDatabase");
        fbMenuRepo.attachPersistentListener(menuKey);
    }


/** Cart Interactions **/
    public LiveData<List<CartItemModel>> getCart()
    {
        return cart;
    }

    public void emptyTheCart()
    {
        cartRepo.emptyTheCart();
    }

    public int getCartItemsCount()
    {
        int count = 0;
        for(CartItemModel cartItem: cart.getValue())
        {
            count += cartItem.getQuantity();
        }
        return count;
    }

    public double getCartSubtotal()
    {
        double subtotal = 0;
        for(CartItemModel cartItem: cart.getValue())
        {
            subtotal += cartItem.getPrice() * cartItem.getQuantity();
        }

        return subtotal;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
//        Log.d("PRINT", "VIEWMODEL CLEARED");
    }
}
