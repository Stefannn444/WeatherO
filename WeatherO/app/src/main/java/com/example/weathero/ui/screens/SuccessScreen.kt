package com.example.weathero.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weathero.BuildConfig
import com.example.weathero.R
import com.example.weathero.data.models.DailyForecast
import com.example.weathero.data.models.HourlyForecast
import com.example.weathero.ui.common.SearchMechanism
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun SuccessScreen(
    weatherViewModel: WeatherViewModel,
    modifier:Modifier = Modifier
){
    LazyColumn(
        modifier=modifier.padding(start=16.dp,end=16.dp)
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            SearchMechanism(weatherViewModel = weatherViewModel)
        }
        item {
            MainWeatherSegment(
                weatherViewModel = weatherViewModel,
                modifier = Modifier.padding(12.dp)
            )
        }
        item {
            HourlyForecastRow(hourlyForecastList = weatherViewModel.hourlyForecast)
        }
        item {
            DailyForecastColumn(dailyForecastList = weatherViewModel.dailyForecast)
        }
    }
}

@Composable
fun MainWeatherSegment(
    weatherViewModel: WeatherViewModel,
    modifier: Modifier=Modifier
){
    Row(
        modifier=modifier.fillMaxWidth()
    ) {
        Column(modifier= Modifier
            .align(Alignment.CenterVertically)
            .weight(1f)
        ) {
            Text(
                text=weatherViewModel.selectedLocation!!.localizedName,
                fontWeight = FontWeight.Bold,
                fontSize=28.sp
            )
            Text(
                text="${((weatherViewModel.hourlyForecast[0].temperature.value-32)*5/9).toInt()}\u00B0C",
                fontSize=52.sp
            )
        }
        AsyncImage(
            model = getWeatherIconId(weatherViewModel.hourlyForecast[0].weatherIcon),
            placeholder = painterResource(id = R.drawable.loading_img),
            error= painterResource(id = R.drawable.ic_broken_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier= Modifier
                .size(150.dp)
                .align(Alignment.CenterVertically)
                .padding(top = 20.dp, start = 36.dp)
            )
    }
}




@Composable
fun HourlyForecastRow(hourlyForecastList: List<HourlyForecast>){
    val nextHours = getNextHours()
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp),modifier=Modifier.background(MaterialTheme.colorScheme.surface)) {
        itemsIndexed(hourlyForecastList){index,_->
            HourlyItem(
                hourlyForecast = hourlyForecastList[index],
                hour = nextHours[index]
            )
        }
    }
}

@Composable
fun HourlyItem(hourlyForecast: HourlyForecast,hour:String){
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(text=hour)
            AsyncImage(
                model = getWeatherIconId(hourlyForecast.weatherIcon),
                placeholder = painterResource(id = R.drawable.loading_img),
                error= painterResource(id = R.drawable.ic_broken_image),
                contentDescription = hourlyForecast.iconPhrase,
                contentScale = ContentScale.Crop,
                modifier= Modifier.size(40.dp)
            )
            Text(
                text= hourlyForecast.iconPhrase,
                fontWeight= FontWeight.ExtraBold
            )

            Text(
                text="${((hourlyForecast.temperature.value-32)*5/9).toInt()}\u00B0C",
            )
            Text(
                text="${hourlyForecast.precipitationProbability}% prec.",
                fontStyle = FontStyle.Italic,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun DailyForecastColumn(dailyForecastList:List<DailyForecast>){
    val nextDays= getNextDays()
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        dailyForecastList.forEachIndexed{index,_->
            DailyForecastItem(
                dailyForecast = dailyForecastList[index],
                day = nextDays[index] )
        }
    }
}

@Composable
fun DailyForecastItem(dailyForecast: DailyForecast,day:String){
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier=Modifier.fillMaxWidth()
    ){
        Row(
            modifier=Modifier.padding(
                start=16.dp,
                end = 16.dp,
                top= 4.dp,
                bottom=4.dp
            ),
        ) {
            Column(
                modifier=Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    "MIN:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold)
                Text("${((dailyForecast.temperature.minimum.value-32)*5/9).toInt()}\u00B0C")
            }
            Column(
                modifier=Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text=day,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                AsyncImage(
                    model = getWeatherIconId(dailyForecast.day.icon),
                    placeholder = painterResource(id = R.drawable.loading_img),
                    error= painterResource(id = R.drawable.ic_broken_image),
                    contentDescription = dailyForecast.day.iconPhrase ,
                    contentScale = ContentScale.Crop,
                    modifier=Modifier.size(32.dp)
                )
            }
            Column(
                modifier=Modifier.weight(1f),
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    "MAX:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold)
                Text("${((dailyForecast.temperature.maximum.value-32)*5/9).toInt()}\u00B0C")
            }
        }
    }
}


private fun getNextDays():List<String>{
    val calendar=Calendar.getInstance()

    val dayFormat=SimpleDateFormat("EEE",Locale.getDefault())
    val nextDays = mutableListOf<String>()

    for(index in 0 until 5){
        val formattedDay = dayFormat.format(calendar.time).uppercase(Locale.getDefault())
        nextDays.add(formattedDay)

        calendar.add(Calendar.DAY_OF_MONTH,1)
    }
    return nextDays
}

private fun getWeatherIconId(iconId:Int):String{
    val formattedIconId = iconId.toString().padStart(2, '0')
    val url=BuildConfig.WEATHER_IMG_LINK
    return url.replace("\${iconString}",formattedIconId)
}

private fun getNextHours():List<String>{
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val currentMinute = Calendar.getInstance().get(Calendar.MINUTE)

    val hoursFormat = SimpleDateFormat("hh:mm a",Locale.getDefault())

    val nextHours = mutableListOf<String>()

    for(index in 0 until 12){
        val hour = (currentHour+index)%24
        val formattedHour= hoursFormat.format(Calendar.getInstance().apply{
                set(Calendar.HOUR_OF_DAY,hour)
                set(Calendar.MINUTE,currentMinute)
            }.time).uppercase(Locale.getDefault())
        nextHours.add(formattedHour)
    }

    return nextHours
}
