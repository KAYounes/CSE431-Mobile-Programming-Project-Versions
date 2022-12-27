package com.example.aklny_v30.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.aklny_v30.models.order_model.OrderModel;
import com.example.aklny_v30.models.order_model.OrderRepo;

import java.util.List;

public class VModel_OrderScreen extends ViewModel {
    private OrderRepo orderRepo;
    private LiveData<OrderModel> order;

    public VModel_OrderScreen() {
        orderRepo = new OrderRepo();
        order = orderRepo.getOrderLive();
    }

    public LiveData<OrderModel> getOrder(){
        return order;
    }

    public void watchOrder(String userKey, String orderKey){
        Log.d("PRINT", "userKey > " + userKey);
        orderRepo.watchOrder(userKey, orderKey);
    }
}