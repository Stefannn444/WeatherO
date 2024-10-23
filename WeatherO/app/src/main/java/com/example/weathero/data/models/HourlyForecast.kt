package com.example.weathero.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyForecast(
    @SerialName("DateTime")
    val dateTime:String,
    @SerialName("EpochDateTime")
    val epochDateTime:Long,
    @SerialName("WeatherIcon")
    val weatherIcon: Int,
    @SerialName("IconPhrase")
    val iconPhrase:String,
    @SerialName("HasPrecipitation")
    val hasPrecipitation: Boolean,
    @SerialName("IsDaylight")
    val isDaylight: Boolean,
    @SerialName("Temperature")
    val temperature: Value,
    @SerialName("PrecipitationProbability")
    val precipitationProbability: Int
)
