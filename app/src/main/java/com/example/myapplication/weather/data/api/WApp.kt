package com.example.myapplication.weather

import com.example.myapplication.weather.data.api.WeatherApiCurrent
import com.example.myapplication.weather.data.api.WeatherApiHourly
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WAppCurrent {

    fun configWAI():Retrofit {

        val httpLogingInterceptor = HttpLoggingInterceptor()
        httpLogingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLogingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    fun retr():WeatherApiCurrent{
        return configWAI().create(WeatherApiCurrent::class.java)
    }
    fun retr2(): WeatherApiHourly {
        return configWAI().create(WeatherApiHourly::class.java)
    }


}