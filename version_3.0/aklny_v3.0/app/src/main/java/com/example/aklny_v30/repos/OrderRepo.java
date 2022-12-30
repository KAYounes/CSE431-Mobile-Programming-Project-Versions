package com.example.aklny_v30.repos;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aklny_v30.Constants;
import com.example.aklny_v30.models.CartItemModel;
import com.example.aklny_v30.models.OrderModel;
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
    private final FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance(Constants.FIREBASE_BASE_URL);
    private final DatabaseReference orderRef = databaseInstance.getReference(Constants.FIREBASE_ORDER_NODE);
    private final MutableLiveData<List<OrderModel>> ordersLive = new MutableLiveData<>();
    private final MutableLiveData<OrderModel> orderLive = new MutableLiveData<>();

    public LiveData<List<OrderModel>> getOrdersLive()
    {
        return ordersLive;
    }
    public LiveData<OrderModel> getOrderLive()
    {
        return orderLive;
    }

    public Task<Void> addOrderModelToFB(String userKey, OrderModel order){
        HashMap<String, Object> orderRefUpdates = new HashMap<>();
        orderRefUpdates.put(userKey + "/" + order.getOrderKey() , order);

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

                        fetchedOrders.add(data.getValue(OrderModel.class));
                    }

                    ordersLive.postValue(fetchedOrders);
                }
                catch (Exception e){
                    Log.e("PRINT", "OrderRepo > attachPersistentListener > onDataChange > Exception > "
                            + e.getLocalizedMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.e("PRINT", "FBMenuRepo > attachPersistentListener > onCancelled > "
                        + error.getMessage());}
        });
    }
    public void watchOrder(String userKey, String orderKey){

        orderRef.child(userKey).child(orderKey).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                try {

                    orderLive.postValue(snapshot.getValue(OrderModel.class));
                }
                catch (Exception e){
                    Log.e("PRINT", "OrderRepo > attachPersistentListener > onDataChange > Exception > "
                            + e.getLocalizedMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.e("PRINT", "FBMenuRepo > attachPersistentListener > onCancelled > "
                        + error.getMessage());}
        });
    }

    public String generateKey(String userKey)
    {
        return orderRef.child(userKey).push().getKey();
    }
}