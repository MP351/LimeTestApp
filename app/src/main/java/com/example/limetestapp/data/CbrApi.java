package com.example.limetestapp.data;

import com.example.limetestapp.data.pojo.Valutes;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CbrApi {
    @GET("daily_json.js")
    Call<Valutes> getValutesList();
}
