package com.example.aklny_v20;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class RestaurantModel implements Parcelable {
    private String name, description;
    private double rating, deliveryFee;
    private int thumbnail;


    public RestaurantModel(String name, String description, double rating, double deliveryFee, int thumbnail) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.deliveryFee = deliveryFee;
        this.thumbnail = thumbnail;
    }

    protected RestaurantModel(Parcel in) {
        name = in.readString();
        description = in.readString();
        rating = in.readDouble();
        deliveryFee = in.readDouble();
        thumbnail = in.readInt();
    }

    public static final Creator<RestaurantModel> CREATOR = new Creator<RestaurantModel>() {
        @Override
        public RestaurantModel createFromParcel(Parcel in) {
            return new RestaurantModel(in);
        }

        @Override
        public RestaurantModel[] newArray(int size) {
            return new RestaurantModel[size];
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
        parcel.writeInt(thumbnail);
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

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
