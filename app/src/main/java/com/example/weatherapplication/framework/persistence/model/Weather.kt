package com.example.weatherapplication.framework.persistence.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class WeatherInfo(val id: Int,
                   val main: String,
                   val description: String,
                   val icon: String)

data class Coord(val lon: Double,
                 val lat: Double)

data class Temperature(val temp: Float,
                val feels_like: Float,
                val temp_min: Float,
                val temp_max: Float,
                val pressure: Int,
                val humidity: Int)

data class Wind(val speed:Float,
                val deg: Int)

@Entity
data class Weather(@Embedded(prefix = "coord_") val coord: Coord,
                   @Embedded(prefix = "weather_info_") val weatherInfo: WeatherInfo,
                   @Embedded val temperature: Temperature,
                   @Embedded val wind: Wind,
                   val base: String,
                   val dt: Long,
                   val timezone: Int,
                   @PrimaryKey val id: Int,
                   val name: String,
                   val cod: Int)