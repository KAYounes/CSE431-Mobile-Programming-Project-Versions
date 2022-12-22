package com.example.aklny_v30.models.databases;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aklny_v30.MenuModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FbMenuRepo {
    FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance("https://aklny-v3-default-rtdb.firebaseio.com/");
    public DatabaseReference menusRef = databaseInstance.getReference("menus");
    public DatabaseReference menusItemRef = databaseInstance.getReference("menuItems");
    private MutableLiveData<MenuModel> fetchedMenu = new MutableLiveData<>();
    public static String recentMenuAddedId;

    private MutableLiveData<List<MenuModel>> fetched = new MutableLiveData<>();


    public LiveData<List<MenuModel>> getFetched() {
        return fetched;
    }

    public Task<Void> addMenuToFbase(String menuKey, MenuModel newMenu){
        return menusRef.child(menuKey).child(newMenu.getTitle()).setValue(newMenu);
    }

//    public Task<Void> addMenuToFbase(String menuKey, MenuModel newMenu){
//        String menuItemKey;
//        for(MenuItemModel menuItem: newMenu.getMenuItems()){
//            menuItemKey = menusRef.child(menuKey).push().getKey();
//            menusRef.child(menuKey).se
//        }
//    }


    public void attachPersistentListener(String menuKey){
        menusRef.child(menuKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
//                    Log.d("menu", "getChildrenCount > " + snapshot.getChildrenCount());
//                    Log.d("menu", "getKey > " + snapshot.getKey());
//                    Log.d("menu", "toString > " + snapshot.toString());
//                    Log.d("menu", "snapshot.getChildren() > " + snapshot.getChildren());
                    List<MenuModel> fetchedMenus = new ArrayList<>();
                    String menuKey;
                    MenuModel menu;
//                    snapshot.getValue().getClass();
                    GenericTypeIndicator<List<MenuModel>> t = new GenericTypeIndicator<List<MenuModel>>() {};
//                    fetchedMenus = snapshot.getValue(t);
//                    Log.d("menu", "snapshot.getValue().getClass > " + snapshot.getValue().getClass());
//                    Log.d("menu", "fetchedMenus.toString > " + fetchedMenus.toString());
//                    Log.d("menu", "fetchedMenus.get(0).toString  > " + fetchedMenus.get(0).toString());


                    for(DataSnapshot data: snapshot.getChildren()){
                        Log.d("menu", "data > " + data.toString());
                        menuKey = data.getKey();
                        menu = data.getValue(MenuModel.class);
                        Log.d("menu", "menu > " + menu.toString());
                        fetchedMenus.add(menu);
                    }

                    fetched.postValue(fetchedMenus)
                    ;}
                catch (Exception e){
                    Log.e("menu", "attachPersistentListener > onDataChange > Exception > "
                            + e.getLocalizedMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public Task<Void> getNewMenuKey(String menuTitle){
        recentMenuAddedId = menusRef.push().getKey();
        return menusRef.child(recentMenuAddedId).setValue(menuTitle);
    }

    public void populate(){

    }

}
