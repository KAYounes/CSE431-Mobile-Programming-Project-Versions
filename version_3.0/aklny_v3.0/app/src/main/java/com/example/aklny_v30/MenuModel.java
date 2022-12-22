package com.example.aklny_v30;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

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
        return "Menu " + title + ", first item is : " + getMenuItems().get(0).getName();
    }
}
