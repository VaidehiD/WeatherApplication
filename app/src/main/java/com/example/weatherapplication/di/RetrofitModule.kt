package com.example.weatherapplication.di

import com.example.weatherapplication.framework.network.service.WeatherApiService
import dagger.Module
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import dagger.Provides
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun getApiInterface(retroFit: Retrofit): WeatherApiService {
        return retroFit.create()
    }

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}