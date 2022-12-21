package com.example.aklny_v30.models.restaurant_model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.Exclude;

public class RestaurantModel implements Parcelable {
//    public static enum cardType{SMALL_CARD, LARGE_CARD};
//    private cardType type;
    private String key, name, description, phoneNumber, address, menuId, logo, thumbnail;
    private Double rating, delivery_fee;



    public RestaurantModel() {}

    public RestaurantModel(String name, String description, String phoneNumber,
                           String address, String logo, String thumbnail,
                           Double rating, Double delivery_fee)
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

    public RestaurantModel(String name, String description, String phoneNumber, String address,
                           String menuId, String logo, String thumbnail, Double rating,
                           Double delivery_fee)
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

    protected RestaurantModel(Parcel in) {
        key = in.readString();
        name = in.readString();
        description = in.readString();
        phoneNumber = in.readString();
        address = in.readString();
        menuId = in.readString();
        logo = in.readString();
        thumbnail = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
        if (in.readByte() == 0) {
            delivery_fee = null;
        } else {
            delivery_fee = in.readDouble();
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Restaurant \nkey: " + key +
                "\nname:" + name +
                "\ndescription:" + description +
                "\nphoneNumber:" + phoneNumber +
                "\naddress:" + address;
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        // If the object is compared with itself then return true
        if (obj == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
        "null instanceof [type]" also returns false */
        if (!(obj instanceof RestaurantModel)) {
        return false;
        }

        RestaurantModel castedObj = (RestaurantModel) obj;

        obj = (RestaurantModel) obj;
        return this.key == castedObj.key
                && this.name == castedObj.name
                && this.description == castedObj.description
                && this.phoneNumber == castedObj.phoneNumber
                && this.address == castedObj.address
                && this.menuId == castedObj.menuId
                && this.logo == castedObj.logo
                && this.thumbnail == castedObj.thumbnail
                && this.rating == castedObj.rating
                && this.delivery_fee == castedObj.delivery_fee;
    }

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

    /** Parcelable **/
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
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(phoneNumber);
        parcel.writeString(address);
        parcel.writeString(menuId);
        parcel.writeString(logo);
        parcel.writeString(thumbnail);
        if (rating == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(rating);
        }
        if (delivery_fee == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(delivery_fee);
        }
    }
}
