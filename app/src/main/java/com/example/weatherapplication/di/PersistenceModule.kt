package com.example.weatherapplication.di

import android.app.Application
import androidx.room.Room
import com.example.weatherapplication.framework.persistence.WeatherDb
import com.example.weatherapplication.framework.persistence.model.Weather
import com.example.weatherapplication.framework.persistence.model.WeatherDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): WeatherDb {
        return Room
            .databaseBuilder(app, WeatherDb::class.java, "weather.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(db: WeatherDb): WeatherDao {
        return db.weatherDao()
    }
}