package com.example.weatherapplication.di

import com.example.weatherapplication.framework.WeatherRepository
import com.example.weatherapplication.framework.network.service.WeatherApiService
import com.example.weatherapplication.framework.persistence.WeatherDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(service: WeatherApiService, db: WeatherDb): WeatherRepository {
        return WeatherRepository(service, db)
    }
}