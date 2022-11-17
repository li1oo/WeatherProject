package com.example.myapplication.weather.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.weather.model.MainViewModel


@Composable
fun CustomDialog(
    message: MutableState<String>,
    openDialog: MutableState<Boolean>,
    editMessage: MutableState<String>,
    myViewModel: MainViewModel
) {
    val cur  = myViewModel.resLDVK.observeAsState().value
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(MaterialTheme.colors.background)

    ) {

            Column(

                modifier = Modifier.padding(vertical = 14.dp),
            ) {
                Text(text = "Поиск", fontSize = 20.sp)

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                    value = editMessage.value,
                    onValueChange = {
                        editMessage.value = it
                        myViewModel.findCity(it)
                        Log.d("ddd", it)
                                    },
                    singleLine = true
                )
            }

            if(cur!= null){
                Column(modifier = Modifier
                    .padding(horizontal = 10.dp),
                    horizontalAlignment = Alignment.Start,
                ){
                    for (item in cur){

                        var name = item.name
                        if(item.country != null)
                            name+= ", " + item.country
                        if(item.state != null)
                            name+= ", " + item.state
                        Text(
                            text = name ,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = {
                                        myViewModel.geoT(item.lat,item.lon)
                                        Log.d("ddd", item.lat)
                                        Log.d("ddd", item.lon)
                                        openDialog.value = false;
                                    }
                                )

                        )
                    }
                }
            }

        Divider(color = MaterialTheme.colors.onSurface, thickness = 1.dp)
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            onClick = {
                openDialog.value = false
            }
        ) {
            Text(text = "Закрыть", color = MaterialTheme.colors.onSurface, fontSize = 18.sp)
        }





    }
}