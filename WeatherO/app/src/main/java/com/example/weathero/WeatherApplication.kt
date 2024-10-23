package com.example.weathero

import android.app.Application
import com.example.weathero.data.AppContainer
import com.example.weathero.data.DefaultAppContainer

class WeatherApplication:Application() {
    lateinit var container:AppContainer
    override fun onCreate() {
        super.onCreate()
        container=DefaultAppContainer()
    }
}