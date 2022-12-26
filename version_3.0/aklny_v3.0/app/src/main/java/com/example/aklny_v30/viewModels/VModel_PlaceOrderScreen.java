package com.example.aklny_v30.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aklny_v30.API.ResponsesModel;
import com.example.aklny_v30.API.ApiRepo;
import com.example.aklny_v30.models.cart.CartItemModel;
import com.example.aklny_v30.models.order_model.OrderModel;
import com.example.aklny_v30.models.order_model.OrderRepo;
import com.example.aklny_v30.models.user_model.UserModel;
import com.example.aklny_v30.repos.CartRepo;
import com.example.aklny_v30.repos.UsersRepo;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class VModel_PlaceOrderScreen extends AndroidViewModel {
    private CartRepo cartRepo;
    private LiveData<List<CartItemModel>> cart;
    private ApiRepo apiRepo;
    private LiveData<ResponsesModel> response;
    private OrderRepo orderRepo;
    private UsersRepo userRepo;
    private FirebaseAuth firebaseAuth;

    public VModel_PlaceOrderScreen(@NonNull Application application) {
        super(application);
        apiRepo = new ApiRepo();
        response = apiRepo.getMyResponse();
        cartRepo = new CartRepo(application);
        cart = cartRepo.getCart();
        orderRepo = new OrderRepo();
        userRepo = new UsersRepo(application);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public LiveData<List<CartItemModel>> getCart(){
        return cart;
    }

    public void getTimeZone(){
        apiRepo.getTimeZone();
    }

    public LiveData<ResponsesModel> getTimeZoneResponse(){
        return  response;
    }

    public Task<Void> addOrder(OrderModel order){
        String userKey = firebaseAuth.getUid();
        Log.d("PRINT", "userKey" + userKey);
        order.setOrderKey(orderRepo.generateKey(userKey));
        return orderRepo.addOrderModelToFB(userKey, order);
    }
}
