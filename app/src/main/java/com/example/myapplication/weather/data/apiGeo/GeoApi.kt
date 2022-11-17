package com.example.myapplication.weather.data.apiGeo

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface GeoApi{

    @GET("direct")
    suspend fun getGeo(@Query("q") q: String,
                           @Query("limit") limit: String,
                           @Query("appid") appid: String): Response<List<GeoResponseList>>


}