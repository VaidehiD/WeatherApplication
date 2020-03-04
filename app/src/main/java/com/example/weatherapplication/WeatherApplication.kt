package com.example.weatherapplication

import android.app.Application
import com.example.weatherapplication.di.AppComponent
import com.example.weatherapplication.di.DaggerAppComponent

open class WeatherApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

}