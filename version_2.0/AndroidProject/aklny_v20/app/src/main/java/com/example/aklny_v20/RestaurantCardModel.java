package com.example.aklny_v20;

import android.os.Parcel;
import android.os.Parcelable;

public class RestaurantCardModel implements Parcelable {
    public enum cardType {CARD_SMALL, CARD_LARGE};
    private cardType type;
    private String name, description;
    private double rating, deliveryFee;
    private int logo, thumbnail;


    public RestaurantCardModel(String name, String description, double rating, double deliveryFee, int logo, int thumbnail) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.deliveryFee = deliveryFee;
        this.logo = logo;
        this.thumbnail = thumbnail;
        this.type = cardType.CARD_LARGE;
    }

    public RestaurantCardModel(String name, String description, double rating, double deliveryFee, int logo) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.deliveryFee = deliveryFee;
        this.logo = logo;
        this.type = cardType.CARD_SMALL;
    }

    protected RestaurantCardModel(Parcel in) {
        name = in.readString();
        description = in.readString();
        rating = in.readDouble();
        deliveryFee = in.readDouble();
        logo = in.readInt();
        thumbnail = in.readInt();
    }

    public static final Creator<RestaurantCardModel> CREATOR = new Creator<RestaurantCardModel>() {
        @Override
        public RestaurantCardModel createFromParcel(Parcel in) {
            return new RestaurantCardModel(in);
        }

        @Override
        public RestaurantCardModel[] newArray(int size) {
            return new RestaurantCardModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeDouble(rating);
        parcel.writeDouble(deliveryFee);
        parcel.writeInt(logo);
        parcel.writeInt(thumbnail);
    }


    public cardType getType() {
        return type;
    }

    public void setType(cardType type) {
        this.type = type;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = RestaurantCardModel.this.thumbnail;
    }
}
