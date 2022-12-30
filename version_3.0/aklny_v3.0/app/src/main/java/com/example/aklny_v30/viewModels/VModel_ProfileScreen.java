package com.example.aklny_v30.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.aklny_v30.models.UserModel;
import com.example.aklny_v30.repos.FBUserRepo;
import com.example.aklny_v30.repos.UsersRepo;
import com.google.firebase.auth.FirebaseAuth;

public class VModel_ProfileScreen extends AndroidViewModel {
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final UsersRepo usersRepo;
    private final FBUserRepo fbUserRepo;

    public VModel_ProfileScreen(@NonNull Application application) {
        super(application);
        usersRepo = new UsersRepo(application);
        fbUserRepo = new FBUserRepo();
    }

    public LiveData<UserModel> getUser() {
        return usersRepo.getUser(firebaseAuth.getUid());
    }

    public void updateUser(UserModel user) {
        usersRepo.updateUser(user);
        fbUserRepo.updateUser(user);
    }
}
