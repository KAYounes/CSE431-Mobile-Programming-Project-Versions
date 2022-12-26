package com.example.aklny_v30.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aklny_v30.models.cart.CartItemModel;
import com.example.aklny_v30.models.menu_model.MenuItemModel;
import com.example.aklny_v30.models.menu_model.MenuModel;
import com.example.aklny_v30.repos.CartRepo;
import com.example.aklny_v30.repos.firebase.FbMenuRepo;

import java.util.List;

public class VModel_MenuItemScreen extends AndroidViewModel
{
    private FbMenuRepo fbMenuRepo;
    private CartRepo cartRepo;
    private LiveData<List<CartItemModel>> cart;

    public VModel_MenuItemScreen(Application application)
    {
        super(application);
        fbMenuRepo = new FbMenuRepo();
        cartRepo = new CartRepo(application);
        cart = cartRepo.getCart();
    }

    /** Cart Interactions **/
    public LiveData<List<CartItemModel>> getCart()
    {
        return cart;
    }

    public void addItemToCart(MenuItemModel menuItemModel)
    {
        Log.d("PRINT", "ViewModel > addItemToCart > item > " + menuItemModel.toString());
        CartItemModel cartItem = new CartItemModel(menuItemModel);
        cartRepo.addItem(cartItem);
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

    public int getItemCountInCart(String key)
    {
        for(CartItemModel cartItem: cart.getValue())
        {
            if(cartItem.getKey().equals(key))
            {
                return cartItem.getQuantity();
            }
        }

        return 0;
    }
}
