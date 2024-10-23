package com.example.weathero.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weathero.ui.screens.HomeScreen
import com.example.weathero.ui.screens.WeatherAppBar
import com.example.weathero.ui.screens.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val weatherViewModel: WeatherViewModel = viewModel(factory = WeatherViewModel.Factory)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { WeatherAppBar(scrollBehavior = scrollBehavior) },
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding),){
            HomeScreen(
                weatherUiState = weatherViewModel.weatherUiState,
                weatherViewModel = weatherViewModel,
                modifier = Modifier
            )

        }
    }
}
