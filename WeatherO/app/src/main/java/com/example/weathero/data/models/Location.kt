package com.example.weathero.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    @SerialName("Key")
    val key:String,
    @SerialName("Type")
    val type: String,
    @SerialName("Rank")
    val rank:Int,
    @SerialName("LocalizedName")
    val localizedName:String,
    @SerialName("EnglishName")
    val englishName:String,
    @SerialName("Region")
    val region:Name,
    @SerialName("Country")
    val country:Name,
    @SerialName("AdministrativeArea")
    val administrativeArea:Area,
    @SerialName("TimeZone")
    val timeZone:TimeZone,
    @SerialName("GeoPosition")
    val geoPosition:Position,

)

@Serializable
data class Area(
    @SerialName("ID")
    val id:String,
    @SerialName("LocalizedName")
    val localizedName: String,
    @SerialName("EnglishName")
    val englishName: String,
    @SerialName("Level")
    val level:Int,
    @SerialName("LocalizedType")
    val localizedType:String,
    @SerialName("EnglishType")
    val englishType:String,
    @SerialName("CountryID")
    val countryId:String
)

@Serializable
data class Name(
    @SerialName("ID")
    val id:String,
    @SerialName("LocalizedName")
    val localizedName: String,
    @SerialName("EnglishName")
    val englishName: String
)

@Serializable
data class TimeZone(
    @SerialName("Code")
    val code:String,
    @SerialName("Name")
    val name:String,
    @SerialName("GmtOffset")
    val gmtOffset:Float,
    @SerialName("IsDaylightSaving")
    val isDaylightSaving:Boolean,

)

@Serializable
data class Position(
    @SerialName("Latitude")
    val latitude:Float,
    @SerialName("Longitude")
    val longitude:Float,
    @SerialName("Elevation")
    val elevation: Elevation,

    )

@Serializable
data class Elevation(
    @SerialName("Metric")
    val metric:Value,
    @SerialName("Imperial")
    val imperial:Value
)

@Serializable
data class Value(
    @SerialName("Value")
    val value:Float,
    @SerialName("Unit")
    val unit: String,
    @SerialName("UnitType")
    val unitType: Int
)