package com.example.myapplication.weather.data.api

import android.os.Build
import android.util.Log

import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.weather.data.apiGeo.GeoApi
import com.example.myapplication.weather.data.apiGeo.GeoResponseList
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt


class WarRep(private val waps : WeatherApiCurrent, private val waps2 : WeatherApiHourly, private val gr: GeoApi) {

    private val resMLD = MutableLiveData<WeatherListResponseCurrent>()
    private val resMLD2 = MutableLiveData<WeatherListResponseHourly>()
    private val resMLDVK = MutableLiveData<List<GeoResponseList>>()
    private val resMLDVK2 = MutableLiveData<WeatherMinMaxList>()
    private val resConnectMLD = MutableLiveData<Boolean>()

    val resLD : LiveData<WeatherListResponseCurrent>
    get() = resMLD

    val resLD2 : LiveData<WeatherListResponseHourly>
    get() = resMLD2

    val resLDVK : LiveData<List<GeoResponseList>>
    get() = resMLDVK

    val resLDVK2 : LiveData<WeatherMinMaxList>
    get() = resMLDVK2

    val resConnectLD : LiveData<Boolean>
    get() = resConnectMLD

    @RequiresApi(Build.VERSION_CODES.O)
    val dateCur = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()





    suspend fun gq(lon:String, lat:String){
            try {
                val res = waps.getWeather(lon,lat,"metric","2deba6bef36cb6d38a85dd09f107982d","ru")


                if(res.body() != null){

                    res.body()!!.main.temp = res.body()!!.main.temp.toFloat().roundToInt().toString() + "°"
                    res.body()!!.main.feels_like = res.body()!!.main.feels_like.toFloat().roundToInt().toString() + "°"
                    res.body()!!.wind.speed = res.body()!!.wind.speed.toFloat().roundToInt().toString() + "м/с"


                    resMLD.postValue(res.body())
                }

            }
            catch (exception: Exception) {
                resConnectMLD.postValue(false)
            }
    }

    suspend fun gq2(lon:String, lat:String){

        try {
            val res = waps2.getWeather(lon,lat,"metric","2deba6bef36cb6d38a85dd09f107982d","ru")
            if(res.body() != null){
                var sep :MutableList<String> = mutableListOf()
                for (i in res.body()!!.list){

                    if (i.dt_txt.dropWhile { it != ' ' } == " 00:00:00") {
                        sep.add(i.main.temp)
                        sep.add(i.weather[0].icon)
                        sep.add(i.dt_txt)
                    }
                    if (i.dt_txt.dropWhile { it != ' ' } == " 12:00:00" && i.dt_txt.dropLastWhile { it != ' ' } != (dateCur+" ")) {
                        sep.add(i.main.temp)
                        sep.add(i.weather[0].icon)
                    }
                }
                var mm: MutableList<WeatherMinMax> = mutableListOf()

                while(sep.size > 4){
                    mm.add(WeatherMinMax(
                        sep[2].removeSuffix(" 00:00:00").dropWhile { it != '-' }.drop(1).replace('-','.'),
                        sep[3].toFloat().roundToInt().toString() + "°",
                        sep[0].toFloat().roundToInt().toString() + "°",
                        "ic"+sep[4],
                        "ic"+sep[1],
                    ))
                    sep.removeAt(0)
                    sep.removeAt(0)
                    sep.removeAt(0)
                    sep.removeAt(0)
                    sep.removeAt(0)
                }


                for(i in res.body()!!.list){
                    i.main.temp = i.main.temp.toFloat().roundToInt().toString() + "°"
                    i.dt_txt  =  " " + (i.dt_txt.dropWhile { it != ' ' }.removeSuffix(":00:00"))+" "
                }
                resMLD2.postValue(res.body())

                resMLDVK2.postValue(WeatherMinMaxList(mm))

            }



        }
        catch (exception: Exception) {
            resConnectMLD.postValue(false)
        }
        resMLDVK.postValue(null)

    }
    fun cl(){
        resMLDVK.postValue(null)
    }

    suspend fun getCities(q: String){

        try {
            val res = gr.getGeo(q, "5", "2deba6bef36cb6d38a85dd09f107982d")

            if(res.body() != null){
                resMLDVK.postValue(res.body())
            }
        }
        catch (exception: Exception) {
            resConnectMLD.postValue(false)
        }
    }
}