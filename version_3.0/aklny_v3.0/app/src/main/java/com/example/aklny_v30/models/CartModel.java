package com.example.aklny_v30.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.aklny_v30.models.menu_model.MenuItemModel;

import java.util.List;

@Entity(tableName = "Cart")
public class CartModel {

    public CartModel(String userId, List<MenuItemModel> items, int subtotal, int item_count) {
        this.userId = userId;
        this.items = items;
        this.subtotal = subtotal;
        this.item_count = item_count;
    }

    @PrimaryKey
    @ColumnInfo(name = "key")
    private String userId;

    @ColumnInfo(name = "items")
    private List<MenuItemModel> items;

    @ColumnInfo(name = "subtotal")
    private int subtotal;

    @ColumnInfo(name = "itemCount")
    private int item_count;
}
