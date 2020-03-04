package com.example.weatherapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.framework.WeatherRepository
import com.example.weatherapplication.framework.persistence.model.Weather
import javax.inject.Inject

class HomePageViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {

    val currentLocation = MutableLiveData<LocationModel>()

    val weatherLiveData: LiveData<Weather> by lazy {
        repository.loadWeatherFromDb()
    }

    fun getWeather(location: String) {
        repository.getWeatherData(location)
    }
}

data class LocationModel(val latitude: Double, val longitude: Double)