package com.example.aklny_v30.API;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MyViewModel extends AndroidViewModel {

    public MyViewModel(@NonNull Application application) {
        super(application);
    }

    private ApiRepo ApiRepo;
    private LiveData<ResponsesModel> response;
    public void init(){
        ApiRepo = new ApiRepo();
        response = ApiRepo.getMyResponse();
    }

    public void getTimeZone(){
        ApiRepo.getTimeZone();
    }

    public LiveData<ResponsesModel> getTimeZoneResponse(){
        return  response;
    }
}
