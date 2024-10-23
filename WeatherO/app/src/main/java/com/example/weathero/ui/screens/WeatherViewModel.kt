package com.example.weathero.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weathero.BuildConfig
import com.example.weathero.WeatherApplication
import com.example.weathero.data.models.DailyForecast
import com.example.weathero.data.models.HourlyForecast
import com.example.weathero.data.models.Location
import com.example.weathero.data.repository.WeatherRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface WeatherUiState{
    data class Success(
        val location:Location,
        val hourlyForecast: List<HourlyForecast>,
        val dailyForecast: List<DailyForecast>
    ):WeatherUiState
    object Loading:WeatherUiState
    object Error:WeatherUiState
    object NoCitySelected:WeatherUiState
}

class WeatherViewModel(
    private val weatherRepository: WeatherRepository
):ViewModel() {

    var weatherUiState:WeatherUiState by  mutableStateOf(WeatherUiState.NoCitySelected)

    var searchQuery:String by mutableStateOf("")
    var selectedLocation:Location? by mutableStateOf(null)

    var locations:List<Location> by mutableStateOf(emptyList())
    var dailyForecast:List<DailyForecast> by mutableStateOf(emptyList())
    var hourlyForecast:List<HourlyForecast> by mutableStateOf(emptyList())

    var errorCode:String by mutableStateOf("")

    fun performSearch(apiKey: String){
        viewModelScope.launch{
            searchLocation(apiKey)
        }
    }

    private suspend fun searchLocation(apiKey:String) {
        weatherUiState = WeatherUiState.Loading
        try {
            var fetchedLocations = weatherRepository.getCity(apiKey, searchQuery)
            if (fetchedLocations.isNotEmpty()) {
                locations = fetchedLocations
                weatherUiState = WeatherUiState.NoCitySelected
            } else {
                errorCode = "DAY"
                weatherUiState = WeatherUiState.Error
            }
        } catch (e: IOException) {
            errorCode = "DAY"
            weatherUiState = WeatherUiState.Error
        } catch (e: HttpException) {
            errorCode = "DAY"
            weatherUiState = WeatherUiState.Error
        }
    }


     fun selectLocation(location:Location) {

         Log.d("WeatherViewModel", "Location selected: ${location.localizedName}")

         selectedLocation = location
         weatherUiState = WeatherUiState.Loading
         viewModelScope.launch {
             getForecast(location.key,BuildConfig.WEATHER_API_KEY)
         }
     }

    suspend fun getForecast(locationKey:String,apiKey: String){
        Log.d("WeatherViewModel", "Fetching forecasts for location key: $locationKey")
        Log.d("API_KEY", "Using API Key: ${BuildConfig.WEATHER_API_KEY}")

        viewModelScope.launch{

            try{
                var fetchedDailyForecast = weatherRepository.getDailyForecasts(locationKey,apiKey)
                var fetchedHourlyForecast = weatherRepository.getHourlyForecasts(locationKey,apiKey)

                if(fetchedDailyForecast.isEmpty()||fetchedHourlyForecast.isEmpty()){
                    Log.e("WeatherViewModel", "Forecast data is empty")
                    errorCode = "FORECAST"
                    weatherUiState=WeatherUiState.Error
                    return@launch
                }else{
                    dailyForecast=fetchedDailyForecast
                    hourlyForecast=fetchedHourlyForecast
                    weatherUiState=WeatherUiState.Success(
                        location=selectedLocation!!,
                        hourlyForecast=hourlyForecast,
                        dailyForecast=dailyForecast
                    )
                    Log.d("WeatherViewModel", "Forecasts fetched successfully")

                }
            }catch(e:IOException){
                Log.e("WeatherViewModel", "Network Error: ${e.message}")
                errorCode = "FORECAST"

                weatherUiState=WeatherUiState.Error
            }catch(e:HttpException){
                Log.e("WeatherViewModel", "HTTP Error: ${e.message()}")
                errorCode = "FORECAST"
                weatherUiState=WeatherUiState.Error
            }
        }
    }

    companion object{
        val Factory:ViewModelProvider.Factory = viewModelFactory {
            initializer{
                val application=(this[APPLICATION_KEY] as WeatherApplication)
                val weatherRepository = application.container.weatherRepository
                WeatherViewModel(weatherRepository=weatherRepository)
            }
        }
    }
}