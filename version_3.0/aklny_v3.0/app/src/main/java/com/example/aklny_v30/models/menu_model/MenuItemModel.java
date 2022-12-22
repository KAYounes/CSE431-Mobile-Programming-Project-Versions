package com.example.aklny_v30.models.menu_model;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuItemModel implements Parcelable {
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

    protected MenuItemModel(Parcel in) {
        name = in.readString();
        description = in.readString();
        thumbnail = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public Double getPrice() {return price;}
    public void setPrice(Double price) {this.price = price;}

    public String getThumbnail() {return thumbnail;}
    public void setThumbnail(String thumbnail) {this.thumbnail = thumbnail;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(thumbnail);
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(price);
        }
    }
}
