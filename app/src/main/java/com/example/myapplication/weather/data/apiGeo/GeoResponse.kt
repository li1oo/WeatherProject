package com.example.myapplication.weather.data.apiGeo

import com.example.myapplication.weather.data.api.WeatherListResponseCurrentMain
import com.example.myapplication.weather.data.api.WeatherListResponseCurrentWeather
import com.example.myapplication.weather.data.api.WeatherListResponseCurrentWind

data class GeoResponseList(
    val name:String,
    val country :String,
    val state :String,
    val lat:String,
    val lon:String,

)
data class GeoResponse(
    val lat:String,
    val lon:String,
    val display_name:String
)
