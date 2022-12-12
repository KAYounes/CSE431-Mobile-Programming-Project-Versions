package com.example.aklny_v20;

public class CartItemModel {
    private String name, description;
    private double basePrice;
    private int count, thumbnail;

    public CartItemModel(String name, String description, double basePrice, int count, int thumbnail) {
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.count = count;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void decreaseCount(){
        if (count > 0 ){
        count -= 1;
        }
    }

    public void increaseCount(){
        if (count < 50 ){
        count += 1;
        }
    }

}
