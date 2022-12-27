package com.example.aklny_v30.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.aklny_v30.models.order_model.OrderModel;
import com.example.aklny_v30.models.order_model.OrderRepo;

import java.util.List;

public class VModel_PreviousOrders extends ViewModel {
    private OrderRepo orderRepo;
    private LiveData<List<OrderModel>> orders;

    public VModel_PreviousOrders() {
        orderRepo = new OrderRepo();
        orders = orderRepo.getOrdersLive();
    }

    public LiveData<List<OrderModel>> getOrders(){
        return orders;
    }

    public void attachPersistentListener(String userKey){
        Log.d("PRINT", "userKey > " + userKey);
        orderRepo.attachPersistentListener(userKey);
    }
}
