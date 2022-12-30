package com.example.aklny_v30.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aklny_v30.models.CartItemModel;
import com.example.aklny_v30.repos.CartRepo;

import java.util.List;

public class VModel_CartScreen extends AndroidViewModel {
    private final CartRepo cartRepo;
    private final LiveData<List<CartItemModel>> cart;

    public VModel_CartScreen(Application application) {
        super(application);
        cartRepo = new CartRepo(application);
        cart = cartRepo.getCart();
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

    public LiveData<List<CartItemModel>> getCart(){
        return cart;
    }
}
