package com.example.aklny_v30.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aklny_v30.models.ResponseModel;
import com.example.aklny_v30.repos.ApiRepo;
import com.example.aklny_v30.models.CartItemModel;
import com.example.aklny_v30.models.OrderModel;
import com.example.aklny_v30.repos.OrderRepo;
import com.example.aklny_v30.repos.CartRepo;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class VModel_PlaceOrderScreen extends AndroidViewModel {
    private final LiveData<List<CartItemModel>> cart;
    private final ApiRepo apiRepo;
    private final LiveData<ResponseModel> response;
    private final OrderRepo orderRepo;
    private final FirebaseAuth firebaseAuth;

    public VModel_PlaceOrderScreen(@NonNull Application application) {
        super(application);
        apiRepo = new ApiRepo();
        response = apiRepo.getMyResponse();
        cart = new CartRepo(application).getCart();
        orderRepo = new OrderRepo();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public LiveData<List<CartItemModel>> getCart(){
        return cart;
    }

    public void getTimeZone(){
        apiRepo.getTimeZone();
    }

    public LiveData<ResponseModel> getTimeZoneResponse(){
        return  response;
    }

    public Task<Void> addOrder(OrderModel order){
        String userKey = firebaseAuth.getUid();

        order.setOrderKey(orderRepo.generateKey(userKey));
        return orderRepo.addOrderModelToFB(userKey, order);
    }
}
