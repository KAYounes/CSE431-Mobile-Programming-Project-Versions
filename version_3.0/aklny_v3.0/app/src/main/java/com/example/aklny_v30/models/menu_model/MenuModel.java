package com.example.aklny_v30.models.menu_model;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;

public class MenuModel {
    private String title;
    private List<MenuItemModel> menuItems;

    public MenuModel() {
    }

    public MenuModel(String title, List menuItems) {
        this.title = title;
        this.menuItems = menuItems;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MenuItemModel> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItemModel> menuItems) {
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public String toString() {
        HashMap<String, Object> print = new HashMap<>();
        print.put("Title", title);
        print.put("Menu Items", menuItems);
//        return "Menu " + title + ", first item is : " + getMenuItems().get(0).getName();
        return print.toString();
    }

}
