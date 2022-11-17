package com.example.myapplication.weather.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.weather.data.api.*
import com.example.myapplication.weather.data.apiGeo.GeoResponseList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(val rep : WarRep) : ViewModel() {



    val resLD : LiveData<WeatherListResponseCurrent>
    get() = rep.resLD

    val resLD2 : LiveData<WeatherListResponseHourly>
    get() = rep.resLD2

    val resLDVK : LiveData<List<GeoResponseList>>
    get() = rep.resLDVK

    val resLDVK2 : LiveData<WeatherMinMaxList>
    get() = rep.resLDVK2

    val resConnectLD : LiveData<Boolean>
        get() = rep.resConnectLD

    fun geoT(lon:String, lat:String){

        viewModelScope.launch (Dispatchers.IO){
            rep.gq(lon,lat)
        }
        viewModelScope.launch (Dispatchers.IO){
            rep.gq2(lon,lat)
        }
    }
    fun cl(){
        rep.cl()
    }
    fun findCity(q:String){

        viewModelScope.launch (Dispatchers.IO){
            rep.getCities(q)
        }
    }


}