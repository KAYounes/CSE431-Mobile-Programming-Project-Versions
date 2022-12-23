package com.example.aklny_v30.repos.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aklny_v30.API.Constants;
import com.example.aklny_v30.models.menu_model.MenuItemModel;
import com.example.aklny_v30.models.menu_model.MenuModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FbMenuRepo {
    public static String recentMenuAddedId;
    private FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance(Constants.FIREBASE_BASE_URL);
    private DatabaseReference menusRef = databaseInstance.getReference(Constants.FIREBASE_MENU_NODE);
    private DatabaseReference menusItemRef = databaseInstance.getReference(Constants.FIREBASE_MENU_ITEMS_NODE);
//    private MutableLiveData<MenuModel> fetchedMenu = new MutableLiveData<>();
    private MutableLiveData<List<MenuModel>> menusLive = new MutableLiveData<>();

    public LiveData<List<MenuModel>> getMenusLive()
    {
        return menusLive;
    }

//    public Task<Void> addMenuModelToFB(String menuKey, MenuModel newMenu){
//        return menusRef.child(menuKey).child(newMenu.getTitle()).setValue(newMenu);
//    }

    public Task<Void> addMenuModelToFB(String menuKey, MenuModel newMenu){
        String menuItemKey;
        List<String> listOfMenuItemKeys = new ArrayList<>();
        HashMap<String, Object> menuRefUpdates = new HashMap<>();
        HashMap<String, Object> menuItemsRefUpdates = new HashMap<>();

        for(MenuItemModel item: newMenu.getMenuItems()){
            menuItemKey = menusRef.child(menuKey).child(newMenu.getTitle()).push().getKey();
            listOfMenuItemKeys.add(menuItemKey);
            menuItemsRefUpdates.put(menuKey + "/" + menuItemKey , item);
            menuRefUpdates.put(menuKey + "/" + newMenu.getTitle() + "/" + menuItemKey, item);
        }

//        menusItemRef.updateChildren(menuItemsRefUpdates)
        return menusRef.updateChildren(menuRefUpdates);
    }


    public void attachPersistentListener(String menuKey){
        menusRef.child(menuKey).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                try {
                    List<MenuModel> fetchedMenus = new ArrayList<>();
                    List<MenuItemModel> menuItems;
                    MenuModel menu;
                    MenuItemModel menuItem;

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

                    menusLive.postValue(fetchedMenus);
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

    public Task<Void> addMenuToFB(String menuTitle)
    {
        recentMenuAddedId = menusRef.push().getKey();
        return menusRef.child(recentMenuAddedId).setValue(menuTitle);
    }
    public String generateKey()
    {
        return menusRef.push().getKey();
    }
}