package com.example.aklny_v30.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.Exclude;

public class RestaurantModel implements Parcelable {
    /** Parcelable **/
    public static final Creator<RestaurantModel> CREATOR = new Creator<RestaurantModel>()
    {
        @Override
        public RestaurantModel createFromParcel(Parcel in)
        {
            return new RestaurantModel(in);
        }

        @Override
        public RestaurantModel[] newArray(int size)
        {
            return new RestaurantModel[size];
        }
    };


    private String key, name, description, phoneNumber, address, menuId, logo, thumbnail;
    private double rating, deliveryFee;

    public RestaurantModel() {}

    public RestaurantModel(String name, String description, String phoneNumber,
                           String address, String logo, String thumbnail,
                           double rating, double deliveryFee)
    {
        this.name = name;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.logo = logo;
        this.thumbnail = thumbnail;
        this.rating = rating;
        this.deliveryFee = deliveryFee;
    }

    public RestaurantModel(String name, String description, String phoneNumber, String address,
                           String menuId, String logo, String thumbnail, double rating,
                           double deliveryFee)
    {
        this.name = name;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.menuId = menuId;
        this.logo = logo;
        this.thumbnail = thumbnail;
        this.rating = rating;
        this.deliveryFee = deliveryFee;
    }

    protected RestaurantModel(Parcel in)
    {
        key = in.readString();
        name = in.readString();
        description = in.readString();
        phoneNumber = in.readString();
        address = in.readString();
        menuId = in.readString();
        logo = in.readString();
        thumbnail = in.readString();
        rating = in.readDouble();
        deliveryFee = in.readDouble();
    }

    @NonNull
    @Override
    public String toString()
    {
        return "Restaurant \nkey: " + key +
                "\nname:" + name +
                "\ndescription:" + description +
                "\nphoneNumber:" + phoneNumber +
                "\naddress:" + address;
    }

    @Override
    public boolean equals(@Nullable Object obj)
    {
        if (obj == this) {return true;}
        if (!(obj instanceof RestaurantModel)) {return false;}

        RestaurantModel castedObj = (RestaurantModel) obj;
        return this.key.equals(castedObj.key)
                && this.name.equals(castedObj.name)
                && this.description.equals(castedObj.description)
                && this.phoneNumber.equals(castedObj.phoneNumber)
                && this.address.equals(castedObj.address)
                && this.menuId.equals(castedObj.menuId)
                && this.logo.equals(castedObj.logo)
                && this.thumbnail.equals(castedObj.thumbnail)
                && this.rating == castedObj.rating
                && this.deliveryFee == castedObj.deliveryFee;
    }

    @Exclude
    public String getKey() {return key;}
    public void setKey(String key) {this.key = key;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    public String getMenuId() {return menuId;}
    public void setMenuId(String menuId) {this.menuId = menuId;}

    public double getRating() {return rating;}
    public void setRating(double rating) {this.rating = rating;}

    public double getDeliveryFee() {return deliveryFee;}
    public void setDeliveryFee(double deliveryFee) {this.deliveryFee = deliveryFee;}

    public String getLogo() {return logo;}
    public void setLogo(String logo) {this.logo = logo;}

    public String getThumbnail() {return thumbnail;}
    public void setThumbnail(String thumbnail) {this.thumbnail = thumbnail;}

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(phoneNumber);
        parcel.writeString(address);
        parcel.writeString(menuId);
        parcel.writeString(logo);
        parcel.writeString(thumbnail);
        parcel.writeDouble(rating);
        parcel.writeDouble(deliveryFee);
    }
}
