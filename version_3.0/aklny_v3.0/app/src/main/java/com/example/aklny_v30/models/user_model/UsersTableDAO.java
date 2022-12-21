package com.example.aklny_v30.models.user_model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsersTableDAO {

    @Query("SELECT * FROM users WHERE uid=:uid")
    LiveData<UserModel> getUser(String uid);

    @Query("SELECT * FROM users")
    LiveData<List<UserModel>> getAllUsers();

    @Query("DELETE FROM users WHERE(uid=:uid)")
    void deleteUser(String uid);

    @Query("DELETE FROM users")
    void deleteAll();

    @Insert
    void addUser(UserModel user);

    @Update
    void updateUser(UserModel user);

//// Read
//    @Query("SELECT uid FROM users WHERE uid=:uid")
//    String getUID(String uid);
//
//    @Query("SELECT first_name FROM users")
//    String getFirstName(String uid);
//
//    @Query("SELECT last_name FROM users")
//    String getLastName(String uid);
//
//    @Query("SELECT phone_number FROM users")
//    String getPhoneNumber(String uid);
//
//    @Query("SELECT email_address FROM users")
//    String getEmail(String uid);
//
//    @Query("SELECT current_order FROM users")
//    String getCurrentOrder(String uid);
//
//    @Query("SELECT photo_url FROM users")
//    String getPhotoURL(String uid);
//
//
//// Update
//    @Query("UPDATE users SET uid=:auth_uid WHERE uid=:uid")
//    void updateUID(String uid, String auth_uid);
//
//    @Query("UPDATE users SET first_name=:firstName WHERE uid=:uid")
//    void updateFirstName(String uid, String firstName);
//
//    @Query("UPDATE users SET last_name=:lastName WHERE uid=:uid")
//    void updateLastName(String uid, String lastName);
//
//    @Query("UPDATE users SET phone_number=:phoneNumber WHERE uid=:uid")
//    void updatePhoneNumber(String uid, String phoneNumber);
//
//    @Query("UPDATE users SET email_address=:email WHERE uid=:uid")
//    void updateEmail(String uid, String email);
//
//    @Query("UPDATE users SET current_order=:currentOrder WHERE uid=:uid")
//    void updateCurrentOrder(String uid, String currentOrder);
//
//    @Query("UPDATE users SET photo_url=:photoURL WHERE uid=:uid")
//    void updatePhotoURL(String uid, String photoURL);

}
