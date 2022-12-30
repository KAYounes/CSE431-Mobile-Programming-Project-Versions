package com.example.aklny_v30.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aklny_v30.API.ApiRepo;
import com.example.aklny_v30.API.Constants;
import com.example.aklny_v30.API.ResponsesModel;
import com.example.aklny_v30.models.user_model.UserModel;
import com.example.aklny_v30.repos.RestaurantRepo;
import com.example.aklny_v30.models.restaurant_model.RestaurantModel;
import com.example.aklny_v30.repos.UsersRepo;
import com.example.aklny_v30.repos.firebase.FbUserRepo;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VModel_HomeScreen extends AndroidViewModel {
    private RestaurantRepo restaurantRepository;
    private LiveData<List<RestaurantModel>> restaurantsList;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ApiRepo apiRepo;
    private Date time;
    private UsersRepo usersRepo;
    private FbUserRepo fbUserRepo;
    private MutableLiveData<String> timeLeft;
    LiveData<UserModel> user;

    public VModel_HomeScreen(@NonNull Application application) {
        super(application);
        usersRepo = new UsersRepo(application);
        restaurantRepository = new RestaurantRepo();
        restaurantsList = restaurantRepository.getFetched();
        apiRepo = new ApiRepo();
        fbUserRepo = new FbUserRepo();
        user = getUser();
    }

    public LiveData<UserModel> getUser(){
        return usersRepo.getUser(firebaseAuth.getUid());
    }

//    public String getUserPhotoUrl(){
//        return user.get.getPhotoURL();
//    }
//
//    public String getUserFirstName(){
//        return user.getLastName();
//    }

    private void setPeriod(){
        apiRepo.getTimeZone();
        ResponsesModel response = apiRepo.getMyResponse().getValue();
        if(response.getHour() > 10){

        }
    }

    public void foo(){
        time = Calendar.getInstance().getTime();
    }

    public void getTimeZone(){
        apiRepo.getTimeZone();
    }

    public LiveData<ResponsesModel> getApiResponse(){
        return apiRepo.getMyResponse();
    }

    public LiveData<List<RestaurantModel>> getFetchedRes() {
//        restaurantsList = restaurantRepository.getFetched();
        return restaurantsList;
    }

    public void watchRestaurants(){
        restaurantRepository.attachPersistentListener();
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
        restaurantRepository.filterRestaurants(filter);
    }

    public void updateLocalUser(UserModel user) {
        usersRepo.updateUser(user);
    }

//    public LiveData<UserModel> getRemoteUser() {
//        return fbUserRepo.getLiveUser(firebaseAuth.getUid());
//    }
    public void watchRemoteUser() {
        fbUserRepo.watchUser(firebaseAuth.getUid());
    }


    public LiveData<UserModel> getRemoteUser() {
        return fbUserRepo.getLiveUser();
    }
}
