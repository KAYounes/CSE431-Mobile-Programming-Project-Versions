package com.example.orders;

public interface DelegateToActivity {
    void updateOrder(String userKey, String orderKey, OrderModel.OrderStatus status);
}
