package com.example.aklny_v30.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aklny_v30.API.ApiRepo;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.example.aklny_v30.models.user_model.UserModel;
import com.example.aklny_v30.repos.RestaurantRepo;
import com.example.aklny_v30.repos.UsersRepo;
import com.example.aklny_v30.repos.firebase.FbUserRepo;
import com.google.firebase.auth.FirebaseAuth;


import java.util.Date;
import java.util.List;

public class VModel_ProfileScreen extends AndroidViewModel {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private UsersRepo usersRepo;
    private FbUserRepo fbUserRepo;
    UserModel user;

    public VModel_ProfileScreen(@NonNull Application application) {
        super(application);
        usersRepo = new UsersRepo(application);
        fbUserRepo = new FbUserRepo();
    }

    public LiveData<UserModel> getUser(){
        return usersRepo.getUser(firebaseAuth.getUid());
    }

    public void updateUser(UserModel user) {
        usersRepo.updateUser(user);
        fbUserRepo.updateUser(user);
    }
}
