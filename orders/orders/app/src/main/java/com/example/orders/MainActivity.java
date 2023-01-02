package com.example.orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.orders.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DelegateToActivity {
    public final String FIREBASE_BASE_URL = "https://aklny-v3-default-rtdb.firebaseio.com/";
    public final String FIREBASE_ORDER_NODE = "orders";
    public final String FIREBASE_USERS_NODE = "users";
    ActivityMainBinding binder;
    private FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance(FIREBASE_BASE_URL);
    private DatabaseReference orderRef = databaseInstance.getReference(FIREBASE_ORDER_NODE);
    public DatabaseReference usersRef = databaseInstance.getReference(FIREBASE_USERS_NODE);
    ListAdapter adapter;
    List<String> userKeys = new ArrayList<>();
    List<String> userNames = new ArrayList<>();
    HashMap<String, String> keysToNames = new HashMap<>();
    List<OrderModel> orders = new ArrayList<>();

    MutableLiveData<HashMap<String, String>> keysToNamesLive = new MutableLiveData<>();
    MutableLiveData<List<OrderModel>> ordersLive = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    UserModel user = snap.getValue(UserModel.class);
                    keysToNames.put(snap.getKey(), user.getFirstName() + " " + user.getLastName());
                }
//                for (OrderModel order: orders){
//                    order.setUserName(keysToNames.get(order.get));
//                }
                keysToNamesLive.postValue(keysToNames);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        keysToNamesLive.observe(this, new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(HashMap<String, String> stringStringHashMap) {
                orderRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap: snapshot.getChildren()){
                            String userKey = snap.getKey();

                            for (DataSnapshot snap2: snap.getChildren()){
                                OrderModel order = snap2.getValue(OrderModel.class);
                                order.setUserKey(userKey);
                                order.setUserName(stringStringHashMap.get(userKey));
                                orders.add(order);
                            }
                        }
                        ordersLive.postValue(orders);
                        orders = new ArrayList<>();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        adapter = new ListAdapter(new ArrayList<>(), this);
        ordersLive.observe(this, new Observer<List<OrderModel>>() {
            @Override
            public void onChanged(List<OrderModel> orderModels) {
                adapter.setData(orderModels);
            }
        });
        binder.orderList.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        binder.orderList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void updateOrder(String userKey, String orderKey, OrderModel.OrderStatus status) {
        orderRef.child(userKey).child(orderKey).child("orderStatus").setValue(status);
    }
}