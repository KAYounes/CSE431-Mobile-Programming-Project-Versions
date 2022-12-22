package com.example.aklny_v30.models;

import androidx.room.Entity;

import com.example.aklny_v30.models.menu_model.MenuItemModel;

import java.util.Map;

@Entity(tableName = "orders")
public class OrderModel {
    private String orderKey;
    private int total;
    private int deliveryFee;
    private int subtotal;
    private int gate;
    private String restaurantKey;
    private Map<MenuItemModel, Integer> items;
}
