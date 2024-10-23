package com.example.weathero.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.weathero.ui.common.SearchMechanism

@Composable
fun SearchScreen(
    weatherViewModel: WeatherViewModel,
    modifier:Modifier
){
    Column(
       verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier=modifier.background(MaterialTheme.colorScheme.surface).fillMaxSize()
    ){
        SearchMechanism(weatherViewModel = weatherViewModel)
    }
}



