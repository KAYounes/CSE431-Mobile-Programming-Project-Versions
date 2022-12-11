package com.example.aklny_v20;

public class MenuItemModel {
    String item_name, item_description;
    Double item_price;
    int item_thumbnail;

    public MenuItemModel(String item_name, String item_description, Double item_price, int item_thumbnail) {
        this.item_name = item_name;
        this.item_description = item_description;
        this.item_price = item_price;
        this.item_thumbnail = item_thumbnail;
    }

    public int getItem_thumbnail() {
        return item_thumbnail;
    }

    public void setItem_thumbnail(int item_thumbnail) {
        this.item_thumbnail = item_thumbnail;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public Double getItem_price() {
        return item_price;
    }

    public void setItem_price(Double item_price) {
        this.item_price = item_price;
    }
}
