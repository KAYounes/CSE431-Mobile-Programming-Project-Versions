package com.example.aklny_v30.models.cart;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.aklny_v30.models.menu_model.MenuItemModel;

@Entity(tableName = "Cart")
public class CartItemModel {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "key")
    private String key;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "thumbnail")
    private String thumbnail;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "quantity")
    private int quantity;

    public CartItemModel() {
    }

    public CartItemModel(MenuItemModel menuItemModel){
        this.key = menuItemModel.getKey();
        this.name = menuItemModel.getName();
        this.description = menuItemModel.getDescription();
        this.thumbnail = menuItemModel.getThumbnail();
        this.price = menuItemModel.getPrice();
        this.quantity = 1;
    }

    public CartItemModel incrementQuantityAndReturn(){
        quantity++;
        return this;
    }

    public CartItemModel decrementQuantityAndReturn(){
        quantity--;
        return this;
    }

    public String getKey() {return key;}
    public void setKey(String key) {this.key = key;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getThumbnail() {return thumbnail;}
    public void setThumbnail(String thumbnail) {this.thumbnail = thumbnail;}

    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}

    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    @NonNull
    @Override
    public String toString() {
        return key + ", " + name + ", " + price+ ", " + quantity;
    }
}
