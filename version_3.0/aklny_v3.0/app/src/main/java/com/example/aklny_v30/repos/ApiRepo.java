package com.example.aklny_v30.repos;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.aklny_v30.API.ApiServices;
import com.example.aklny_v30.Constants;
import com.example.aklny_v30.models.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepo {
    private final ApiServices apiServices;
    private final MutableLiveData<ResponseModel> myResponse;

    public ApiRepo(){
        myResponse = new MutableLiveData<>();
        apiServices = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiServices.class);
    }

    public void getTimeZone(){
        apiServices.getTimeZone()
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        myResponse.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Log.e("PRINT", "ERROR API > " + t.getMessage());
                        myResponse.setValue(null);
                    }
                });
    }

    public LiveData<ResponseModel> getMyResponse() {
        return myResponse;
    }
}
