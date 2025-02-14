# WeatherO
A weather forecast Android app

## Features
* The ability to search for **any location recognized by the AccuWeather API**, then show the forecast **for the current time, for the next 12 hours and for the next 5 days**  
* **UI**: developed through **Compose**  
* **MVVM Architecture**  
* **Network, RESTful API Calls**: the forecast is requested through **Retrofit**. Additionally, **Coil** is tasked with obtaining the pictures describing the weather condition  

## Installation
* Clone this repository
* Obtain your AccuWeather API key
* In your local.properties file:
```
WEATHER_API_KEY=                                                                           //paste the aforementioned API key
WEATHER_IMG_LINK=https://developer.accuweather.com/sites/default/files/${iconString}-s.png // be wary, for this link is prone to change
```
* Build and run the app.
