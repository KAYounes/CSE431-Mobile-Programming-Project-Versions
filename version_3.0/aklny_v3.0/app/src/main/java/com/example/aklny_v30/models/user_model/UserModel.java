package com.example.aklny_v30.models.user_model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

@Entity(tableName = "users")
public class UserModel {
    public UserModel() {
    }

    public UserModel(@NonNull String auth_uid, @NonNull String firstName, @NonNull String lastName, @NonNull String phoneNumber, @NonNull String email) {
        this.auth_uid = auth_uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
//
//    @NonNull
//    @Override
//    public String toString() {
//        String user = auth_uid + ": "
//                + firstName + " " + lastName
//                + ", > " + phoneNumber
//                + ", > " + email;
//        return user;
//    }

    @NonNull
    @Override
    public String toString() {
        super.toString();
        String user= "";
        String tab = "\t", newLine="\n";
        user += "------------";
        user += newLine + auth_uid;
        user += newLine + tab + firstName + " " + lastName;
        user += newLine + tab + phoneNumber;
        user += newLine + tab + email;
        return user;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "uid")
    private String auth_uid;

    @NonNull
    @ColumnInfo(name = "first_name")
    private String firstName;

    @NonNull
    @ColumnInfo(name = "last_name")
    private String lastName;

    @NonNull
    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    @NonNull
    @ColumnInfo(name = "email_address")
    private String email;

    @ColumnInfo(name = "current_order")
    private String currentOrder;

    @ColumnInfo(name = "photo_url")
    private String photoURL;



/*--------------------------------------Getters and Setters--------------------------------------*/

    @NonNull
    @Exclude
    public String getAuth_uid() {
        return auth_uid;
    }

    public void setAuth_uid(@NonNull String auth_uid) {
        this.auth_uid = auth_uid;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NonNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(String currentOrder) {
        this.currentOrder = currentOrder;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
