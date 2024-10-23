package com.example.weathero.data.repository

import com.example.weathero.data.models.DailyForecast
import com.example.weathero.data.models.HourlyForecast
import com.example.weathero.data.models.Location
import com.example.weathero.data.network.WeatherApi

interface WeatherRepository{
    suspend fun getCity(apiKey:String, cityName:String):List<Location>
    suspend fun getDailyForecasts(locationKey:String,apiKey: String):List<DailyForecast>
    suspend fun getHourlyForecasts(locationKey: String,apiKey: String):List<HourlyForecast>
}

class NetworkWeatherRepository(
    private val weatherApi: WeatherApi
):WeatherRepository{
    override suspend fun getCity(apiKey: String, cityName: String): List<Location> = weatherApi.getCity(apiKey,cityName)
    override suspend fun getDailyForecasts(locationKey: String, apiKey: String): List<DailyForecast> = weatherApi.getDailyForecasts(locationKey,apiKey).dailyForecasts
    override suspend fun getHourlyForecasts(locationKey: String, apiKey: String): List<HourlyForecast> = weatherApi.getHourlyForecasts(locationKey,apiKey)
}