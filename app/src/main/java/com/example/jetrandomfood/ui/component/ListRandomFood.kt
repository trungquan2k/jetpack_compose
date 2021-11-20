package com.example.jetrandomfood.ui.component


import android.widget.Toast

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


import kotlin.random.Random



@ExperimentalMaterialApi
@Composable
fun ListRanDomFoods(){
    // Get random id of 1 product in data
    val getFoodID = List(1) { Random.nextInt(insertFoodsData.size) }

    // toast
    val context = LocalContext.current
    // check if get any id in data  list
    if (getFoodID.size != null) {
        Column() {
            LazyColumn(modifier = Modifier.padding(10.dp)) {
                items(getFoodID.size) { itemIndex ->
                    // Create a variable that stores query objects by random id
                    val randomElement = insertFoodsData[getFoodID[itemIndex]]
                    ListDataRoom(food = randomElement)
                }
            }
        }
    } else {
        Toast.makeText(
            context,
            "Nothing",
            Toast.LENGTH_LONG,
        ).show()
    }
}