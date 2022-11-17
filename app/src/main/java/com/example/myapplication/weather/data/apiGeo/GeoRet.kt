package com.example.myapplication.weather.data.apiGeo

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object GeoRet {

    fun configGR(): Retrofit {

        val httpLogingInterceptor = HttpLoggingInterceptor()
        httpLogingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLogingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/geo/1.0/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    fun retr(): GeoApi {
        return configGR().create(GeoApi::class.java)
    }



}