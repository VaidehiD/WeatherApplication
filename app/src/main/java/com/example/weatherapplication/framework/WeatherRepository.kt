package com.example.weatherapplication.framework

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.weatherapplication.framework.network.service.WeatherApiService
import com.example.weatherapplication.framework.persistence.WeatherDb
import com.example.weatherapplication.framework.persistence.model.Weather
import com.example.weatherapplication.utility.toWeatherModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(val service: WeatherApiService,
                                            val database: WeatherDb) {

    private fun fetchFromNetwork(location: String) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response =
                    service.getWeather(location, "f93cf0f184083b53e18bc1437a7ea473")
                Log.d("Name", "Location = ${response.name}")

                database.weatherDao().insert(response.toWeatherModel())

            } catch (exception: Exception) {
                Log.e("WeatherExcpetion", "Exception : ${exception.message}")
            }
        }
    }

    fun getWeatherData(location: String): LiveData<Weather> {
        if (shouldFetchWeatherFromNetwork()) {
            fetchFromNetwork(location)
        }
        return loadWeatherFromDb()
    }

    private fun shouldFetchWeatherFromNetwork(): Boolean {
        // Todo : Check weather fetched more than 10 mins
        return true;
    }

    fun loadWeatherFromDb(): LiveData<Weather> {
        // Todo : Fetch for specific location
        return database.weatherDao().getAll()
    }
}