package com.example.aklny_v30.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aklny_v30.models.CartItemModel;
import com.example.aklny_v30.models.MenuItemModel;
import com.example.aklny_v30.repos.CartRepo;
import com.example.aklny_v30.repos.FBMenuRepo;

import java.util.List;

public class VModel_MenuItemScreen extends AndroidViewModel
{
//    private final FBMenuRepo fbMenuRepo;
    private final CartRepo cartRepo;
    private final LiveData<List<CartItemModel>> cart;

    public VModel_MenuItemScreen(Application application)
    {
        super(application);
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
