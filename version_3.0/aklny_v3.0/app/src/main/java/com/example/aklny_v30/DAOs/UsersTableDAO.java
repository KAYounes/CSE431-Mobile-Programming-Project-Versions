package com.example.aklny_v30.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.aklny_v30.models.UserModel;

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

}
