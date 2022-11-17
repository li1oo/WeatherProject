package com.example.myapplication.weather.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.colBut

import com.example.myapplication.weather.model.MainViewModel


@Composable
fun current_view(myViewModel: MainViewModel, dialog : MutableState<Boolean>){

    val cur  = myViewModel.resLD.observeAsState().value
    Column() {

        if(cur != null){
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment  = Alignment.CenterHorizontally
            ) {


                Text(
                    color = Color.White,
                    text = cur.name,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 39.dp),
                    textAlign = TextAlign.Center
                )
                ClickableText(
                    text = AnnotatedString("Выбрать другой город"),
                    style = TextStyle(
                        color = colBut,
                        textDecoration = TextDecoration.Underline,
                        fontSize = 20.sp
                    ),
                    onClick = {
                        dialog.value = true
                    },

                )

                Text(
                    color = Color.White,
                    text = cur.weather[0].description.replaceFirstChar{ it.uppercase()},
                    maxLines = 2,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(top = 20.dp),
                    textAlign = TextAlign.Center
                )
                Row(modifier = Modifier
                    .padding(top = 20.dp)
                    .height(IntrinsicSize.Min)) {
                    val context = LocalContext.current

                    val drawableId by derivedStateOf {context.resources.getIdentifier(
                        "ic" + cur.weather[0].icon,
                        "drawable",
                        context.packageName
                    ) }
                    if (drawableId != null) {
                        Image(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(6.dp),
                            contentScale = ContentScale.Fit,
                            painter = painterResource(id = drawableId),
                            contentDescription = null,

                        )
                    }
                    Text(
                        color = Color.White,
                        text = cur.main.temp,
                        maxLines = 2,
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            color = Color.White,
                            text = cur.main.humidity + "%",
                            maxLines = 1,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            color = Color.White,
                            text = "Влажность",
                            maxLines = 1,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    Column(

                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            color = Color.White,
                            text = cur.main.feels_like,
                            maxLines = 1,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            color = Color.White,
                            text = "Ощущается",
                            maxLines = 1,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            color = Color.White,
                            text = cur.wind.speed,
                            maxLines = 1,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            color = Color.White,
                            text = "Ветер",
                            maxLines = 1,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Text(

                    color = Color.White,
                    text = "Почасовой прогноз погоды",
                    maxLines = 1,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(top = 39.dp, bottom = 10.dp),
                    textAlign = TextAlign.Center
                )
                Divider(color = Color.White, thickness = 1.dp)

            }
        }
    }

}
@Composable
fun horly_view(myViewModel: MainViewModel){

    val cur  = myViewModel.resLD2.observeAsState().value
    if(cur!= null) {

    LazyRow(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()
    ){

            items(cur.list.subList(0, 8)) { message ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(12.dp)
                        .padding(horizontal = 10.dp)
                        .width(IntrinsicSize.Max)

                    ,
                    ) {

                    val context = LocalContext.current

                    val drawableId by derivedStateOf {context.resources.getIdentifier(
                        "ic" + message.weather[0].icon,
                        "drawable",
                        context.packageName
                    ) }
                    Text(
                        modifier = Modifier
                            .padding(bottom = 4.dp),
                        color = Color.White,
                        text = message.dt_txt,
                        maxLines = 1,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,

                        )
                    Text(
                        modifier = Modifier
                            .padding(bottom = 4.dp),
                        color = Color.White,
                        text = message.main.temp,
                        maxLines = 1,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                    if (drawableId != null) {
                        Image(
                            modifier = Modifier,
                            contentScale = ContentScale.Fit,

                            painter = painterResource(id = drawableId),
                            contentDescription = null,
                        )
                    }

                }
            }
        }
        Divider(color = Color.White, thickness = 1.dp)
    }


}
@Composable
fun dayly_view(myViewModel: MainViewModel){

    val cur  = myViewModel.resLDVK2.observeAsState().value
    Column(

        horizontalAlignment  = Alignment.CenterHorizontally) {

        if(cur!= null) {
        Text(
            color = Color.White,
            text = "Прогноз погоды на ближайшие дни",
            maxLines = 1,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 10.dp),
            textAlign = TextAlign.Center
        )
            Divider(color = Color.White, thickness = 1.dp)
        LazyColumn(modifier = Modifier
            .padding(20.dp)){

                items(cur.list) { message ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .height(IntrinsicSize.Max)
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                        Text(

                            text = message.date,
                            maxLines = 1,
                            color = Color.White,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                        Row(

                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {

                            val context = LocalContext.current
                            val drawableId by derivedStateOf {context.resources.getIdentifier(
                                 message.idd,
                                "drawable",
                                context.packageName
                            ) }
                            Image(
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .padding(top = 4.dp)
                                    .width(22.dp),
                                contentScale = ContentScale.Fit,

                                painter = painterResource(id = drawableId),
                                contentDescription = null,
                            )
                            Text(
                                modifier = Modifier,
                                color = Color.White,
                                text = message.temp_max,
                                maxLines = 1,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                            val drawableId2 by derivedStateOf {context.resources.getIdentifier(
                                message.idn,
                                "drawable",
                                context.packageName
                            ) }

                            Image(
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .width(22.dp)
                                    .padding(top = 4.dp),
                                contentScale = ContentScale.Fit,

                                painter = painterResource(id = drawableId2),
                                contentDescription = null,
                            )

                            Text(
                                modifier = Modifier,
                                color = colBut,
                                text = message.temp_min,
                                maxLines = 1,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                }
            }

        }
    }

}