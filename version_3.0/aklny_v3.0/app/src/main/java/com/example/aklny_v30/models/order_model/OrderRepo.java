package com.example.aklny_v30.models.order_model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aklny_v30.API.Constants;
import com.example.aklny_v30.models.cart.CartItemModel;
import com.example.aklny_v30.models.menu_model.MenuItemModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Orders -> user-key -> order-key ->
public class OrderRepo {
    private FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance(Constants.FIREBASE_BASE_URL);
    private DatabaseReference orderRef = databaseInstance.getReference(Constants.FIREBASE_ORDER_NODE);
    private MutableLiveData<List<OrderModel>> ordersLive = new MutableLiveData<>();

    public LiveData<List<OrderModel>> getOrdersLive()
    {
        return ordersLive;
    }

//    public Task<Void> addOrderModelToFB(String menuKey, OrderModel newMenu){
//        return menusRef.child(menuKey).child(newMenu.getTitle()).setValue(newMenu);
//    }

    public Task<Void> addOrderModelToFB(String userKey, OrderModel order){
        HashMap<String, Object> orderRefUpdates = new HashMap<>();
        orderRefUpdates.put(userKey + "/" + order.getOrderKey() , order);

//        for(CartItemModel item: order.getCart()){
//            orderRefUpdates.put(userKey + "/" + order.getOrderKey() + "/cart/" + item.getKey() , item);
//        }

        return orderRef.updateChildren(orderRefUpdates);
    }


    public void attachPersistentListener(String userKey){
        orderRef.child(userKey).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                try {
                    List<OrderModel> fetchedOrders = new ArrayList<>();
                    List<CartItemModel> cart;
                    OrderModel order;
                    CartItemModel cartItem;

                    for(DataSnapshot data: snapshot.getChildren()){
                        order = new OrderModel();
                        cart = new ArrayList<>();
                        Log.d("PRINT", "data > " + data.toString());
                    }

//                    ordersLive.postValue(fetchedOrders);
                }
                catch (Exception e){
                    Log.e("PRINT", "OrderRepo > attachPersistentListener > onDataChange > Exception > "
                            + e.getLocalizedMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.e("PRINT", "FbMenuRepo > attachPersistentListener > onCancelled > "
                        + error.getMessage());}
        });
    }

    public String generateKey(String userKey)
    {
        return orderRef.child(userKey).push().getKey();
    }
}