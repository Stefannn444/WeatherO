package com.example.weathero.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.weathero.BuildConfig
import com.example.weathero.data.models.Location
import com.example.weathero.ui.screens.WeatherUiState
import com.example.weathero.ui.screens.WeatherViewModel

@Composable
fun SearchMechanism(weatherViewModel:WeatherViewModel){
    SearchField(
        value = weatherViewModel.searchQuery,
        onValueChange = {
            weatherViewModel.searchQuery=it
        },
        onSearchClicked = {
            weatherViewModel.performSearch(apiKey = BuildConfig.WEATHER_API_KEY)
        },
        modifier=Modifier.padding(0.dp)
    )

    if(weatherViewModel.locations.isNotEmpty()&&weatherViewModel.weatherUiState is WeatherUiState.NoCitySelected){
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier=Modifier.fillMaxSize()
        ) {
            items(weatherViewModel.locations){location->
                LocationItem(
                    location=location,
                    onClick={
                        weatherViewModel.selectLocation(location)
                    },
                    modifier=Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun SearchField(
    value:String,
    onValueChange: (String)->Unit,
    onSearchClicked:()->Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Search for a city") },
        modifier = modifier.fillMaxWidth(),
        trailingIcon = {
            Button(
                onClick = onSearchClicked,
                modifier = modifier.padding(end = 12.dp)
            ) {
                Text("Search")
            }
        },
        shape = RoundedCornerShape(120.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions { onSearchClicked() }
    )
}

@Composable
fun LocationItem(
    location: Location,
    onClick: ()->Unit,
    modifier: Modifier =Modifier
){
    Card(
        modifier= modifier
            .clickable(onClick = onClick)
            .padding(8.dp)

    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier=modifier
        ) {

            Text(
                text="${location.localizedName}, ${location.administrativeArea.id}",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            Text(
                text = location.country.localizedName,
                fontWeight = FontWeight.Thin,
                textAlign = TextAlign.Center

            )
        }
    }
}