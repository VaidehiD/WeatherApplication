package com.example.weatherapplication.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.R
import com.example.weatherapplication.WeatherApplication
import com.example.weatherapplication.databinding.ActivityMainBinding
import com.example.weatherapplication.framework.persistence.model.Weather
import com.example.weatherapplication.utility.LocationUtils
import com.example.weatherapplication.utility.getSingleLineAddress
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import javax.inject.Inject

class HomePageActivity : AppCompatActivity() {

    @Inject lateinit var viewModeFactory: ViewModelFactory<HomePageViewModel>
    lateinit var homePageViewModel: HomePageViewModel

    private val LOCATION_PERMISSION_ID= 101
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    @Inject lateinit var geoCoder: Geocoder

    lateinit var bindingView: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as WeatherApplication).appComponent.inject(this)

        bindingView = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bindingView.lifecycleOwner = this

        homePageViewModel = ViewModelProvider(this, viewModeFactory).get(HomePageViewModel::class.java)
        bindingView.weatherViewModel = homePageViewModel

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

        homePageViewModel.weatherLiveData.observe(this, Observer{
            Log.d("HomePageActivity", "Weather data : $it")

            if (!it?.name.isNullOrEmpty()) {
                updateUIWithWeatherData(it)
            }
        } )

        homePageViewModel.currentLocation.observe(this, Observer {
            Toast.makeText(this.applicationContext, "Latitude : ${it.latitude}, Longitude : ${it.longitude}", Toast.LENGTH_LONG).show()
            val location = geoCoder.getSingleLineAddress(it.latitude, it.longitude)
            bindingView.weatherView.location.text = location
            homePageViewModel.getWeather(location)
        })
    }

    private fun updateUIWithWeatherData(weather: Weather) {
        bindingView.weatherView.root.visibility = View.VISIBLE
        bindingView.weatherProgressBar.visibility = View.GONE

        Picasso.get().load("https://openweathermap.org/img/wn/${weather.weatherInfo.icon}@2x.png").into(bindingView.weatherView.imageView)
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    private fun getLastLocation() {
        if (LocationUtils.checkPermissions(applicationContext)) {
            if (LocationUtils.isLocationEnabled(applicationContext)) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        homePageViewModel.currentLocation.postValue(LocationModel(location.latitude, location.longitude))
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
//               Todo: Show view with message to enable from settings
            }
        } else {
            requestPermissions()
        }
    }

    private fun requestNewLocationData() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            LocationUtils.getNewLocationRequest(), mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            homePageViewModel.currentLocation.postValue(LocationModel(mLastLocation.latitude, mLastLocation.longitude))
            Toast.makeText(applicationContext, "Location - latitude : ${mLastLocation.latitude} longitude : ${mLastLocation.longitude}", Toast.LENGTH_LONG).show()
        }
    }
}
