package com.example.weathero.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyForecastResponse(
    @SerialName("Headline")
    val headline:Headline,
    @SerialName("DailyForecasts")
    val dailyForecasts:List<DailyForecast>
)

@Serializable
data class Headline(
    @SerialName("EffectiveDate")
    val effectiveDate:String
)

@Serializable
data class DailyForecast(
    @SerialName("Date")
    val date:String,
    @SerialName("EpochDate")
    val epochDate:Long,
    @SerialName("Temperature")
    val temperature:Temperature,
    @SerialName("Day")
    val day:DayNightStat,
    @SerialName("Night")
    val night:DayNightStat
)

@Serializable
data class Temperature(
    @SerialName("Minimum")
    val minimum:Value,
    @SerialName("Maximum")
    val maximum:Value
)

@Serializable
data class DayNightStat(
    @SerialName("Icon")
    val icon:Int,
    @SerialName("IconPhrase")
    val iconPhrase: String,
    @SerialName("HasPrecipitation")
    val hasPrecipitation: Boolean
)