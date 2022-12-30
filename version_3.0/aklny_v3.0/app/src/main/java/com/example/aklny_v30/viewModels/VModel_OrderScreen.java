package com.example.aklny_v30.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.aklny_v30.models.OrderModel;
import com.example.aklny_v30.repos.OrderRepo;

public class VModel_OrderScreen extends ViewModel {
    private final OrderRepo orderRepo;
    private final LiveData<OrderModel> order;

    public VModel_OrderScreen() {
        orderRepo = new OrderRepo();
        order = orderRepo.getOrderLive();
    }

    public LiveData<OrderModel> getOrder(){
        return order;
    }

    public void watchOrder(String userKey, String orderKey){
        orderRepo.watchOrder(userKey, orderKey);
    }
}