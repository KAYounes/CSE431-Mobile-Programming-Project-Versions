package com.example.aklny_v30.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.aklny_v30.models.order_model.OrderModel;
import com.example.aklny_v30.models.order_model.OrderRepo;
import com.example.aklny_v30.models.user_model.UserModel;
import com.example.aklny_v30.repos.UsersRepo;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class VModel_PreviousOrders extends AndroidViewModel {
    private OrderRepo orderRepo;
    private UsersRepo usersRepo;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private LiveData<List<OrderModel>> orders;

    public VModel_PreviousOrders(@NonNull Application application) {
        super(application);
        orderRepo = new OrderRepo();
        orders = orderRepo.getOrdersLive();
        usersRepo = new UsersRepo(application);
    }

    public LiveData<List<OrderModel>> getOrders(){
        return orders;
    }

    public void attachPersistentListener(String userKey){
//        Log.d("PRINT", "userKey > " + userKey);
        orderRepo.attachPersistentListener(userKey);
    }

    public LiveData<UserModel> getUser(){
        return usersRepo.getUser(firebaseAuth.getUid());
    }
}
