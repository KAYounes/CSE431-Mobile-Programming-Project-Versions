package com.example.aklny_v30.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aklny_v30.repos.ApiRepo;
import com.example.aklny_v30.Constants;
import com.example.aklny_v30.models.ResponseModel;
import com.example.aklny_v30.models.UserModel;
import com.example.aklny_v30.repos.FBRestaurantRepo;
import com.example.aklny_v30.models.RestaurantModel;
import com.example.aklny_v30.repos.UsersRepo;
import com.example.aklny_v30.repos.FBUserRepo;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VModel_HomeScreen extends AndroidViewModel {
    private final FBRestaurantRepo FBRestaurantRepository;
    private final LiveData<List<RestaurantModel>> restaurantsList;
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final ApiRepo apiRepo;
    private final UsersRepo usersRepo;
    private final FBUserRepo fbUserRepo;
    LiveData<UserModel> user;
//    private Date time;
    private MutableLiveData<String> timeLeft;

    public VModel_HomeScreen(@NonNull Application application) {
        super(application);
        usersRepo = new UsersRepo(application);
        FBRestaurantRepository = new FBRestaurantRepo();
        restaurantsList = FBRestaurantRepository.getFetched();
        apiRepo = new ApiRepo();
        fbUserRepo = new FBUserRepo();
        user = getUser();
    }

    public LiveData<UserModel> getUser() {
        return usersRepo.getUser(firebaseAuth.getUid());
    }

    public void getTimeZone() {
        apiRepo.getTimeZone();
    }

    public LiveData<ResponseModel> getApiResponse() {
        return apiRepo.getMyResponse();
    }

    public LiveData<List<RestaurantModel>> getFetchedRes() {
        return restaurantsList;
    }

    public void watchRestaurants() {
        FBRestaurantRepository.attachPersistentListener();
    }


    public String getTimeUntil10(int hour, int minute) {
        int minutesLeft = 60 - minute;
        int hoursLeft = Constants.FIRST_PERIOD - 1 - hour;
        return hoursLeft + "H " + minutesLeft + " M";
    }

    public String getTimeUntil13(int hour, int minute) {
        int minutesLeft = 60 - minute;
        int hoursLeft = Constants.SECOND_PERIOD - 1 - hour;
        return hoursLeft + "H " + minutesLeft + " M";
    }

    public void filterRestaurants(String filter) {
        FBRestaurantRepository.filterRestaurants(filter);
    }

    public void updateLocalUser(UserModel user) {
        usersRepo.updateUser(user);
    }

    public void watchRemoteUser() {
        fbUserRepo.watchUser(firebaseAuth.getUid());
    }


    public LiveData<UserModel> getRemoteUser() {
        return fbUserRepo.getLiveUser();
    }
}
