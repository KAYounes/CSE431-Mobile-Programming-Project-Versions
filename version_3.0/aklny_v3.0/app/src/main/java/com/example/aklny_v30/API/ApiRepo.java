package com.example.aklny_v30.API;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepo {
    private ApiServices apiServices;
    private MutableLiveData<ResponsesModel> myResponse;

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
                .enqueue(new Callback<ResponsesModel>() {
                    @Override
                    public void onResponse(Call<ResponsesModel> call, Response<ResponsesModel> response) {
                        myResponse.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<ResponsesModel> call, Throwable t) {
                        myResponse.setValue(null);
                    }
                });
    }

    public LiveData<ResponsesModel> getMyResponse() {
        return myResponse;
    }
}
