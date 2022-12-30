package com.example.aklny_v30.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aklny_v30.models.OrderModel;
import com.example.aklny_v30.repos.OrderRepo;
import com.example.aklny_v30.models.UserModel;
import com.example.aklny_v30.repos.UsersRepo;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class VModel_PreviousOrders extends AndroidViewModel {
    private final OrderRepo orderRepo;
    private final UsersRepo usersRepo;
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final LiveData<List<OrderModel>> orders;

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

        orderRepo.attachPersistentListener(userKey);
    }

    public LiveData<UserModel> getUser(){
        return usersRepo.getUser(firebaseAuth.getUid());
    }
}
