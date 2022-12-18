package com.example.aklny_v30.models;

import com.example.aklny_v30.models.user_model.UserModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUsersRepository {
    FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance("https://aklny-v3-default-rtdb.firebaseio.com/");
    public DatabaseReference usersRef = databaseInstance.getReference("users");
    private UserModel user;

    public Task<DataSnapshot> getUser(String uid)
    {
        return usersRef.child(uid).get();
    }

    public Task<DataSnapshot> userExists(String uid)
    {
        return usersRef.child(uid).get();
    }

    public Task<Void> addUser(UserModel newUser) {
        return usersRef.child(newUser.getAuth_uid()).setValue(newUser);
    }
}