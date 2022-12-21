package com.example.aklny_v30.models.databases;

import androidx.lifecycle.MutableLiveData;

import com.example.aklny_v30.models.menu_model.MenuModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FbMenuRepo {
    FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance("https://aklny-v3-default-rtdb.firebaseio.com/");
    public DatabaseReference menusRef = databaseInstance.getReference("menus");
    private MutableLiveData<MenuModel> fetchedMenu = new MutableLiveData<>();
    public String lastMenuId;

    public Task<Void> getNewMenuKey(String restaurantName){
        lastMenuId = menusRef.push().getKey();
        return menusRef.child(lastMenuId).setValue(restaurantName);
    }


}
