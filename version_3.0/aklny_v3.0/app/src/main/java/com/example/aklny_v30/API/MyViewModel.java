package com.example.aklny_v30.API;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MyViewModel extends AndroidViewModel {

    public MyViewModel(@NonNull Application application) {
        super(application);
    }

    private repo repo;
    private LiveData<ResponsesModel> response;
    public void init(){
        repo = new repo();
        response = repo.getMyResponse();
    }

    public void getTimeZone(){
        repo.getTimeZone();
    }

    public LiveData<ResponsesModel> getTimeZoneResponse(){
        return  response;
    }
}
