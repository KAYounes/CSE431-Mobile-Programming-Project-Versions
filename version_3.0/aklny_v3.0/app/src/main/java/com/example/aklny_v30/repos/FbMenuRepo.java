package com.example.aklny_v30.repos;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aklny_v30.models.menu_model.MenuItemModel;
import com.example.aklny_v30.models.menu_model.MenuModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
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

    public Task<Void> addMenuToFbase2(String menuKey, MenuModel newMenu){
        String itemKey;
        List<String> keys = new ArrayList<>();
        HashMap<String, Object> childUpdates = new HashMap<>();
        HashMap<String, Object> childUpdates2 = new HashMap<>();

        for(MenuItemModel item: newMenu.getMenuItems()){
            itemKey = menusRef.child(menuKey).child(newMenu.getTitle()).push().getKey();
            keys.add(itemKey);
            childUpdates.put(menuKey + "/" + itemKey , item);
            childUpdates2.put(menuKey + "/" + newMenu.getTitle() + "/" + itemKey, item);
        }

//        menusRef.child(menuKey).child(newMenu.getTitle()).setValue(keys);
        return menusRef.updateChildren(childUpdates2);
//        return menusItemRef.updateChildren(childUpdates);
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
                    List<MenuModel> fetchedMenus = new ArrayList<>();
                    List<MenuItemModel> menuItems;
                    MenuModel menu;
                    MenuItemModel menuItem;

//                    for(DataSnapshot data: snapshot.getChildren()){
//                        menuKey = data.getKey();
//                        menu = data.getValue(MenuModel.class);
//                        fetchedMenus.add(menu);
//                    }

                    for(DataSnapshot data: snapshot.getChildren()){
                        menu = new MenuModel();
                        menuItems = new ArrayList<>();
                        menu.setTitle(data.getKey());
                        Log.d("menu", "menu title > " + menu.getTitle());

                        for(DataSnapshot item: data.getChildren()){
                            menuItem = item.getValue(MenuItemModel.class);
                            menuItem.setKey(item.getKey());
                            menuItems.add(menuItem);
                        }

                        menu.setMenuItems(menuItems);
                        fetchedMenus.add(menu);
                    }

                    fetched.postValue(fetchedMenus);
                }
                catch (Exception e){
                    Log.e("PRINT", "attachPersistentListener > onDataChange > Exception > "
                            + e.getLocalizedMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public Task<Void> getNewMenuKey(String menuTitle) {
        recentMenuAddedId = menusRef.push().getKey();
        return menusRef.child(recentMenuAddedId).setValue(menuTitle);
    }
}