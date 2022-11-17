package com.example.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.weather.model.MainViewModel
import com.example.myapplication.weather.model.ViewModelFactory
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.ActivityCompat
import androidx.lifecycle.*
import com.example.myapplication.ui.theme.colEG
import com.example.myapplication.ui.theme.colSG
import com.example.myapplication.weather.views.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm = ViewModelProvider(this, ViewModelFactory()).get(MainViewModel::class.java)
        vm.resConnectLD.observe(this, Observer {
            if(it == false){
                Toast.makeText(applicationContext, "Ошибка соединения", LENGTH_SHORT ).show()
            }
        })

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                requestGeo(vm)
            } else {
                vm.geoT("55.7504461","37.6174943")
            }
        }
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        setContent {
            MyApplicationTheme {
                full_view(vm)
            }
        }
    }
    private fun requestGeo(vm: MainViewModel){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        }
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token)
            .addOnSuccessListener { location -> if (location != null) {

                vm.geoT(location.latitude.toString(),location.longitude.toString())
            } }
            .addOnFailureListener{
                vm.geoT("55.7504461","37.6174943")
            }


    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    MyApplicationTheme {
        //login_view()

    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun full_view(myViewModel: MainViewModel){
    val message = remember { mutableStateOf("Город") }
        val gradient = Brush.verticalGradient(listOf(colSG, colEG))
    val openDialog = remember { mutableStateOf(false) }
    val editMessage = remember { mutableStateOf("") }


    Column(modifier = Modifier
        .fillMaxSize()
        .background(gradient)
        ) {

        current_view(myViewModel, openDialog)
        horly_view(myViewModel)
        dayly_view(myViewModel)

    }
        if (!openDialog.value){
            editMessage.value=""
            myViewModel.cl()
        }

        if (openDialog.value) {
            Dialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                properties = DialogProperties(usePlatformDefaultWidth = false),
            ) {
                    CustomDialog(message, openDialog, editMessage, myViewModel)
            }
        }


}
