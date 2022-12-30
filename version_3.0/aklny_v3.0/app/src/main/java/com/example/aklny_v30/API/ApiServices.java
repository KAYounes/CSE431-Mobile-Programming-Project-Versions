package com.example.aklny_v30.API;

import com.example.aklny_v30.models.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {
    @GET("zone?timeZone=Africa/Cairo")
    Call<ResponseModel> getTimeZone();
}
