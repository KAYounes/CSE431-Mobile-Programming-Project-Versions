package com.example.aklny_v30.repos.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aklny_v30.models.menu_model.MenuItemModel;
import com.example.aklny_v30.models.menu_model.MenuModel;
import com.example.aklny_v30.models.user_model.UserModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FbUserRepo {
    FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance("https://aklny-v3-default-rtdb.firebaseio.com/");
    public DatabaseReference usersRef = databaseInstance.getReference("users");
    private MutableLiveData<UserModel> user = new MutableLiveData<>();;

    public Task<DataSnapshot> getUser(String uid)
    {
        return usersRef.child(uid).get();
    }

//    public Task<DataSnapshot> userExists(String uid)
//    {
//        return usersRef.child(uid).get();
//    }

    public Task<Void> addUser(UserModel newUser) {
        return usersRef.child(newUser.getAuth_uid()).setValue(newUser);
    }

    public LiveData<UserModel> getLiveUser(){
        return user;
    }

    public void watchUser(String userKey){
        usersRef.child(userKey).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                try {
                    UserModel fetchedUser = new UserModel();
                    fetchedUser = snapshot.getValue(UserModel.class);
                    fetchedUser.setAuth_uid(snapshot.getKey());
                    user.postValue(fetchedUser);
                }
                catch (Exception e){
                    Log.e("PRINT", "FbMenuRepo > attachPersistentListener > onDataChange > Exception > "
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

    public void updateUser(UserModel user) {
        usersRef.child(user.getAuth_uid()).setValue(user);
    }
}
