package com.example.weatherapplication.framework.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapplication.framework.persistence.model.Weather
import com.example.weatherapplication.framework.persistence.model.WeatherDao

@Database(entities = [(Weather::class)], version = 1, exportSchema = false)
abstract class WeatherDb : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}