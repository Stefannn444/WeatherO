package com.example.weathero.ui.screens

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun HomeScreen(
    weatherUiState: WeatherUiState,
    weatherViewModel: WeatherViewModel,
    modifier:Modifier=Modifier
) {
    when (weatherUiState) {
        is WeatherUiState.Error -> ErrorScreen {
            if (weatherViewModel.errorCode == "DAY") {
                weatherViewModel.weatherUiState = WeatherUiState.NoCitySelected
            } else if (weatherViewModel.errorCode == "FORECAST") {
                weatherViewModel.selectLocation(weatherViewModel.selectedLocation!!)
            }
        }

        is WeatherUiState.Loading -> LoadingScreen()
        is WeatherUiState.NoCitySelected -> SearchScreen(weatherViewModel, modifier)
        is WeatherUiState.Success -> SuccessScreen(weatherViewModel,modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(scrollBehavior: TopAppBarScrollBehavior) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "WeatherO",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}