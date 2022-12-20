package com.example.aklny_v30.models.restaurant_model;

import com.google.firebase.database.Exclude;

public class RestaurantModel {
//    public static enum cardType{SMALL_CARD, LARGE_CARD};
//    private cardType type;
    private String key, name, description, phoneNumber, address, menuId, logo, thumbnail;
    private Double rating, delivery_fee;

//    public RestaurantModel(String name, String description, Double rating, Double delivery_fee, int logo) {
//        this.name = name;
//        this.description = description;
//        this.rating = rating;
//        this.delivery_fee = delivery_fee;
//        this.logo = logo;
//        this.type = cardType.SMALL_CARD;
//    }

//    public RestaurantModel(String name, String description, Double rating, Double delivery_fee, int logo, int thumbnail) {
//        this.name = name;
//        this.description = description;
//        this.rating = rating;
//        this.delivery_fee = delivery_fee;
//        this.logo = logo;
//        this.thumbnail = thumbnail;
//        this.type = cardType.LARGE_CARD;
//    }


    public RestaurantModel(
            String name,
            String description,
            String phoneNumber,
            String address,
            String logo,
            String thumbnail,
            Double rating,
            Double delivery_fee
    )
    {
        this.name = name;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.logo = logo;
        this.thumbnail = thumbnail;
        this.rating = rating;
        this.delivery_fee = delivery_fee;
    }

    public RestaurantModel(
            String name,
            String description,
            String phoneNumber,
            String address,
            String menuId,
            String logo,
            String thumbnail,
            Double rating,
            Double delivery_fee
    )
    {
        this.name = name;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.menuId = menuId;
        this.logo = logo;
        this.thumbnail = thumbnail;
        this.rating = rating;
        this.delivery_fee = delivery_fee;
    }

//    public cardType getType() {
//        return type;
//    }
//    public void setType(cardType type) {
//        this.type = type;
//    }

    @Exclude
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getMenuId() {
        return menuId;
    }
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getDelivery_fee() {
        return delivery_fee;
    }
    public void setDelivery_fee(Double delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
