package com.example.aklny_v30.models.cart;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.aklny_v30.models.cart.CartItemModel;

import java.util.List;

@Dao
public interface CartTableDAO {

    @Query("SELECT * FROM cart")
    LiveData<List<CartItemModel>> getCart();

    @Query("DELETE FROM cart")
    void emptyTheCart();

    @Query("SELECT * FROM cart WHERE `key`=:key")
    CartItemModel getCartItem(String key);

    @Query("SELECT SUM(quantity) FROM cart")
    int getNumberOfItems();

    @Query("SELECT price*quantity AS subtotal FROM cart")
    double getSubtotal();

    @Insert
    void addItem(CartItemModel item);

    @Query("DELETE FROM cart WHERE `key`=:key")
    void removeItem(String key);

    @Update
    void updateItem(CartItemModel item);
}
