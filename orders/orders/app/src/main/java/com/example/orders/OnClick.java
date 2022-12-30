package com.example.orders;

public interface OnClick {
    void onClick(String userKey, String orderKey, OrderModel.OrderStatus status);
}
