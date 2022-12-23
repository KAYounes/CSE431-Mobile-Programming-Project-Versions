package com.example.aklny_v30.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartTableDAO {

    @Query("SELECT * FROM cart")
    LiveData<List<CartItemModel>> getCart();

    @Query("DELETE FROM cart")
    void deleteCart();

    @Query("SELECT * FROM cart WHERE `key`=:key")
    CartItemModel getCartItem(String key);

    @Query("SELECT SUM(quantity) FROM cart")
    int getNumberOfItems();

    @Query("SELECT price*quantity AS subtotal FROM cart")
    double getSubtotal();

    @Insert
    void addItem(CartItemModel item);

    @Delete
    void removeItem(CartItemModel cartItemModel);

    @Update
    void updateItem(CartItemModel item);
}
