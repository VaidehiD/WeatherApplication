package com.example.weatherapplication.utility

import android.location.Geocoder
import com.example.weatherapplication.framework.persistence.model.*
import com.example.weatherapplication.framework.network.model.Weather as WeatherResponse

fun WeatherResponse.toWeatherModel(): Weather {

    val coord = this.coord
    val weatherInfo = this.weather[0]
    val temperature = this.main
    val wind = this.wind

    return Weather(
        Coord(coord.lon, coord.lat),
        WeatherInfo(weatherInfo.id, weatherInfo.main, weatherInfo.description, weatherInfo.icon),
        Temperature(
            temperature.temp,
            temperature.feels_like,
            temperature.temp_min,
            temperature.temp_max,
            temperature.pressure,
            temperature.humidity
        ),
        Wind(wind.speed, wind.deg),
        this.base,
        this.dt,
        this.timezone,
        this.id,
        this.name,
        this.cod
    )
}

fun Geocoder.getSingleLineAddress(latitude: Double, longitude: Double): String {
    val addresses = this.getFromLocation(
        latitude,
        longitude,
        1
    )
    return addresses[0].subLocality?: addresses[0].locality
}

fun getTemperatureInCelsius(tempInKelvin: Float) = String.format("%.2f", tempInKelvin - 273.15f)