package com.example.weatherapplication.framework.network.model

import com.squareup.moshi.Json

data class WeatherInfo(val id: Int,
                   val main: String,
                   val description: String,
                   val icon: String)

data class Coord(val lon: Double,
                 val lat: Double)

data class Main(val temp: Float,
                val feels_like: Float,
                val temp_min: Float,
                val temp_max: Float,
                val pressure: Int,
                val humidity: Int)

data class Wind(val speed:Float,
                val deg: Int)

data class Weather(val coord: Coord,
                              @Json(name = "weather") val weather: List<WeatherInfo>,
                              val main: Main,
                              val wind: Wind,
                              val base: String,
                              val dt: Long,
                              val timezone: Int,
                              val id: Int,
                              val name: String,
                              val cod: Int) {
}