package com.example.weatherapplication.di

import android.app.Application
import android.content.Context
import com.example.weatherapplication.ui.HomePageActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    RetrofitModule::class,
    RepositoryModule::class,
    PersistenceModule::class,
    AppModule::class])
interface AppComponent {

    fun inject(homePageActivity: HomePageActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}