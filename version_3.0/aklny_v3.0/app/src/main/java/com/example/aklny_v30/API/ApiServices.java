package com.example.aklny_v30.API;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {
    @GET("zone?timeZone=Africa/Cairo")
    Call<ResponsesModel> getTimeZone();
}
