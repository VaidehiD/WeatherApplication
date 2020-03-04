package com.example.weatherapplication.di

import android.app.Application
import android.location.Geocoder
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class AppModule {

    @Provides
    fun provideGeoCoder(app: Application): Geocoder {
        return Geocoder(app, Locale.getDefault())
    }
}