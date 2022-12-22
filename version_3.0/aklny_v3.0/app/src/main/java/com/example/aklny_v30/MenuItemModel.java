package com.example.aklny_v30;

public class MenuItemModel {
    private String name, description, thumbnail;
    private Double price;

    public MenuItemModel() {
    }


    public MenuItemModel(String name, String description, Double price, String thumbnail) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public Double getPrice() {return price;}
    public void setPrice(Double price) {this.price = price;}

    public String getThumbnail() {return thumbnail;}
    public void setThumbnail(String thumbnail) {this.thumbnail = thumbnail;}
}
