package com.example.aklny_v20;

import java.util.ArrayList;

public class SubmenuModel {
    private ArrayList<MenuItemModel> subMenuItems;
    private String title;

    public SubmenuModel(ArrayList<MenuItemModel> subMenuItems, String title) {
        this.subMenuItems = subMenuItems;
        this.title = title;
    }

    public ArrayList<MenuItemModel> getSubMenuItems() {
        return subMenuItems;
    }

    public void setSubMenuItems(ArrayList<MenuItemModel> subMenuItems) {
        this.subMenuItems = subMenuItems;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
