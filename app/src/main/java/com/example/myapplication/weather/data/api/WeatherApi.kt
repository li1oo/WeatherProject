package com.example.myapplication.weather.data.api


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiCurrent{

    @GET("weather")
    suspend fun getWeather(@Query("lat") uid: String,
                                    @Query("lon") latitude: String,
                                    @Query("units") latitud: String,
                                    @Query("appid") lattude: String,
                                    @Query("lang") longitude: String): Response<WeatherListResponseCurrent>


}
interface WeatherApiHourly{

    @GET("forecast")
    suspend fun getWeather(@Query("lat") uid: String,
                           @Query("lon") latitude: String,
                           @Query("units") latitud: String,
                           @Query("appid") lattude: String,
                           @Query("lang") longitude: String): Response<WeatherListResponseHourly>

}
