package com.example.weathero.data

import com.example.weathero.data.network.WeatherApi
import com.example.weathero.data.repository.NetworkWeatherRepository
import com.example.weathero.data.repository.WeatherRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val weatherRepository:WeatherRepository
}

class DefaultAppContainer:AppContainer{
    private val baseUrl = "https://dataservice.accuweather.com/"

    val json = Json{
        ignoreUnknownKeys=true
        coerceInputValues=true
    }

    private val retrofit:Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: WeatherApi by lazy{
        retrofit.create(WeatherApi::class.java)
    }

    override val weatherRepository:WeatherRepository by lazy{
        NetworkWeatherRepository(retrofitService)
    }
}