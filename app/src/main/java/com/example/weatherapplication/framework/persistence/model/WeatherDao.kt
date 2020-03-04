package com.example.weatherapplication.framework.persistence.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather")
    fun getAll(): LiveData<Weather>

    @Query("SELECT * FROM weather WHERE coord_lat = :lat AND coord_lon = :lon")
    fun getWeatherForCoord(lat: Double, lon: Double): LiveData<Weather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: Weather)

    @Delete
    fun delete(weather: Weather)
}
