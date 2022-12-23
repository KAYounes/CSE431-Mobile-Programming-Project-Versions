package com.example.aklny_v30.models.menu_model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MenuItemModel implements Parcelable{
    public static final Creator<MenuItemModel> CREATOR = new Creator<MenuItemModel>() {
        @Override
        public MenuItemModel createFromParcel(Parcel in) {
            return new MenuItemModel(in);
        }

        @Override
        public MenuItemModel[] newArray(int size) {
            return new MenuItemModel[size];
        }
    };
    private String key, name, description, thumbnail;
    private double price;

    public MenuItemModel() {
    }


    public MenuItemModel(String name, String description, Double price, String thumbnail) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    protected MenuItemModel(Parcel in) {
        key = in.readString();
        name = in.readString();
        description = in.readString();
        thumbnail = in.readString();
        price = in.readDouble();
    }

    public String getKey() {return key;}
    public void setKey(String key) {this.key = key;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}

    public String getThumbnail() {return thumbnail;}
    public void setThumbnail(String thumbnail) {this.thumbnail = thumbnail;}

    @NonNull
    @Override
    public String toString() {
        return key + ", " + name + ", " + price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(thumbnail);
        parcel.writeDouble(price);
    }
}
