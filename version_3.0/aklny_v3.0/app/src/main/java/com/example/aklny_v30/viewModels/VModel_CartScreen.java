package com.example.aklny_v30.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aklny_v30.models.cart.CartItemModel;
import com.example.aklny_v30.models.menu_model.MenuItemModel;
import com.example.aklny_v30.repos.CartRepo;

import java.util.List;

public class VModel_CartScreen extends AndroidViewModel {
    private CartRepo cartRepo;
    private LiveData<List<CartItemModel>> cart;

    public VModel_CartScreen(Application application) {
        super(application);
        cartRepo = new CartRepo(application);
        cart = cartRepo.getCart();
    }

    public void addItemToCart(MenuItemModel menuItemModel){
        Log.d("PRINT", "ViewModel > addItemToCart > item > " + menuItemModel.toString());
        CartItemModel cartItem = new CartItemModel(menuItemModel);
        cartRepo.addItem(cartItem);
    }

    public void emptyTheCart(){
        cartRepo.emptyTheCart();
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

    public void removeItem(String key){
        cartRepo.removeItem(key);
    }

    public void updateItem(CartItemModel item){
        if(item.getQuantity() == 0){
            cartRepo.removeItem(item.getKey());
            return;
        }
        cartRepo.updateItem(item);
    }

    public double getTotal(){
        return 0.0;
    }


    public LiveData<List<CartItemModel>> getCart(){
        return cart;
    }
}
