package com.example.aklny_v30.models;

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

    @NonNull
    @Override
    public String toString() {
        return "User: " + auth_uid
                + ", Email : " + email
                + ", First name: " + firstName
                + ", Last name: " + lastName
                + ", Phone number: " + phoneNumber
                + ", Photo URL: " + photoURL;
    }


    public String display() {
        return "User: " + auth_uid
                + "\n\tEmail\t\t\t\t     := " + email
                + "\n\tFirst name\t\t   := " + firstName
                + "\n\tLast name\t\t\t := " + lastName
                + "\n\tPhone number\t := " + phoneNumber;
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

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}