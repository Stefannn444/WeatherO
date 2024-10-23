package com.example.weathero.data.network

import com.example.weathero.data.models.DailyForecastResponse
import com.example.weathero.data.models.HourlyForecast
import com.example.weathero.data.models.Location
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    @GET("locations/v1/cities/search")
    suspend fun getCity(
        @Query("apikey") apiKey:String,
        @Query("q") cityName:String,
    ):List<Location>

    @GET("forecasts/v1/daily/5day/{locationKey}")
    suspend fun getDailyForecasts(
        @Path("locationKey") locationKey:String,
        @Query("apikey") apiKey: String
    ):DailyForecastResponse

    @GET("forecasts/v1/hourly/12hour/{locationKey}")
    suspend fun getHourlyForecasts(
        @Path("locationKey") locationKey: String,
        @Query("apikey") apiKey: String
    ):List<HourlyForecast>
}