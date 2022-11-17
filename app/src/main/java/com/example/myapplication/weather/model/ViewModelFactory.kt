package com.example.myapplication.weather.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.weather.WAppCurrent
import com.example.myapplication.weather.data.api.WarRep
import com.example.myapplication.weather.data.apiGeo.GeoRet

class ViewModelFactory() : ViewModelProvider.Factory {
    val w = WAppCurrent.retr()
    val w2 = WAppCurrent.retr2()
    val gr = GeoRet.retr()
    val waprep = WarRep(w,w2,gr)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(waprep) as T
    }

}