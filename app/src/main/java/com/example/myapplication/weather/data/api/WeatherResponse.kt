package com.example.myapplication.weather.data.api

data class WeatherListResponseCurrent(
    val weather: List<WeatherListResponseCurrentWeather>,
    var main: WeatherListResponseCurrentMain,
    val wind: WeatherListResponseCurrentWind,
    val name: String
)
data class WeatherListResponseCurrentWeather(
    val description: String,
    val icon: String
)
data class WeatherListResponseCurrentMain(
    var temp: String,
    var feels_like: String,
    val humidity: String,
    val temp_min: String,
    val temp_max: String,
)
data class WeatherListResponseCurrentWind(
    var speed: String
)


data class WeatherListResponseHourly(
    val list: List<WeatherListResponseHourlyList>
)
data class WeatherListResponseHourlyList(
    val main: WeatherListResponseCurrentMain,
    var dt_txt: String,
    val weather: List<WeatherListResponseCurrentWeather>,
)

data class WeatherMinMaxList(
    val list: List<WeatherMinMax>
)
data class WeatherMinMax(
    var date: String,
    val temp_max: String,
    val temp_min: String,
    val idd: String,
    val idn: String,
)
