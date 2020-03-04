package com.example.weatherapplication.framework.network.service

import com.example.weatherapplication.framework.network.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather/?")
    suspend fun getWeather(@Query("q") location: String,
                           @Query("appId") appId: String): Weather
}