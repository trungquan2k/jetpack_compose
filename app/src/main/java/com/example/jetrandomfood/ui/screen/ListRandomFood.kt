package com.example.jetrandomfood.ui.screen

import android.app.Application
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetrandomfood.data.database.model.FoodDbModel
import com.example.jetrandomfood.viewmodel.MainViewModel
import com.example.jetrandomfood.domain.FoodModel
import com.example.jetrandomfood.routing.JetFoodRouter
import com.example.jetrandomfood.routing.Screen
import com.example.jetrandomfood.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch
import kotlin.random.Random


@ExperimentalMaterialApi
@Composable
fun ListRanDomFood(viewModel: MainViewModel) {
    val foodEntry: FoodModel by viewModel.foodEntry.observeAsState(FoodModel())

    val foods: List<FoodDbModel> by viewModel.foods.observeAsState(listOf())


    val bottomDrawerState: BottomDrawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val scope = rememberCoroutineScope()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    BackHandler(onBack = {
        if (bottomDrawerState.isOpen) {
            coroutineScope.launch { bottomDrawerState.isClosed }
        } else {
            JetFoodRouter.navigateTo(Screen.Home)
        }
    })

    val myRandomValues = List(3) { Random.nextInt(insertFoodsData.size) }
    val context = LocalContext.current
    val backgroundShape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(4.dp)
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            com.example.jetrandomfood.appdrawer.TopAppBar(
                scaffoldState = scaffoldState,
                scope = scope
            )
        },
        content = {
            if (foodEntry.id != null) {
                Column() {
                    LazyColumn(modifier = Modifier.padding(10.dp)) {
                        items(myRandomValues.size) { itemIndex ->
                            val randomElement = insertFoodsData[myRandomValues[itemIndex]]
                            Row(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .shadow(1.dp, backgroundShape)
                                    .fillMaxWidth()
                                    .heightIn(min = 64.dp)
                                    .background(Color.White, backgroundShape)
                            ) {
                                Image(
                                    painter = painterResource(id = randomElement.image),
                                    contentDescription = "Round corner image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(96.dp)
                                        .clip(RoundedCornerShape(10))
                                        .border(1.dp, Color.Gray, RoundedCornerShape(10))
                                )
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .align(Alignment.CenterVertically)
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = randomElement.name,
                                        maxLines = 1,
                                        color = Color.Black,
                                        style = TextStyle(
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 16.sp,
                                            letterSpacing = 0.15.sp
                                        )
                                    )
                                    Text(
                                        text = randomElement.price,
                                        maxLines = 1,
                                        color = Color.Black.copy(alpha = 0.75f),
                                        style = TextStyle(
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 14.sp,
                                            letterSpacing = 0.25.sp
                                        )
                                    )
                                }
                            }
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
        },

        )
}


@Composable
fun ListFoodsItems(foods: List<FoodDbModel>) {

    val context = LocalContext.current
    val roomViewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(context.applicationContext as Application)
    )
    roomViewModel.addFood(insertFoodsData)
//    val foods: List<FoodModel> by roomViewModel.foods.observeAsState(listOf())

//    val getAllRecord = roomViewModel.readAllData.observeAsState(listOf()).value // fetching the data


    Column() {
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            items(foods.size) { itemIndex ->
                val color = foods[itemIndex]
                if (foods.isNotEmpty()) {
                    ListFood(color)
                } else {
                    Toast.makeText(
                        context,
                        "Thank for you choose",
                        Toast.LENGTH_LONG,

                        ).show()
                }
            }
        }
    }
}


@Composable
fun ListFood(foodEntry: FoodDbModel) {
    val backgroundShape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(4.dp)
    Row(
        modifier = Modifier
            .padding(8.dp)
            .shadow(1.dp, backgroundShape)
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .background(Color.White, backgroundShape)
    ) {
        Image(
            painter = painterResource(id = foodEntry.image),
            contentDescription = "Round corner image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(10))
                .border(1.dp, Color.Gray, RoundedCornerShape(10))
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(8.dp)
        ) {
            Text(
                text = foodEntry.name,
                maxLines = 1,
                color = Color.Black,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    letterSpacing = 0.15.sp
                )
            )
            Text(
                text = foodEntry.price,
                maxLines = 1,
                color = Color.Black.copy(alpha = 0.75f),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    letterSpacing = 0.25.sp
                )
            )
        }
    }
}


@Composable
fun ListFoodsRandom(foods: List<FoodModel>) {
    val backgroundShape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(4.dp)

    Row(
        modifier = Modifier
            .padding(8.dp)
            .shadow(1.dp, backgroundShape)
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .background(Color.White, backgroundShape)
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(foods.size) { itemIndex ->
                val food = foods[itemIndex]
                Image(
                    painter = painterResource(id = food.image),
                    contentDescription = "Round corner image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(10))
                        .border(1.dp, Color.Gray, RoundedCornerShape(10))
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(8.dp)
                ) {
                    Text(
                        food.name,
                        maxLines = 1,
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            letterSpacing = 0.15.sp
                        )
                    )
                    Text(
                        text = food.price,
                        maxLines = 1,
                        color = Color.Black.copy(alpha = 0.75f),
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            letterSpacing = 0.25.sp
                        )
                    )
                }
            }
        }
//        Image(
//            painter = painterResource(id = food.image),
//            contentDescription = "Round corner image",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .size(96.dp)
//                .clip(RoundedCornerShape(10))
//                .border(1.dp, Color.Gray, RoundedCornerShape(10))
//        )
//        Column(
//            modifier = Modifier
//                .weight(1f)
//                .align(Alignment.CenterVertically)
//                .padding(8.dp)
//        ) {
//            Text(
//                food.name,
//                maxLines = 1,
//                color = Color.Black,
//                style = TextStyle(
//                    fontWeight = FontWeight.Normal,
//                    fontSize = 16.sp,
//                    letterSpacing = 0.15.sp
//                )
//            )
//            Text(
//                text =food.price,
//                maxLines = 1,
//                color = Color.Black.copy(alpha = 0.75f),
//                style = TextStyle(
//                    fontWeight = FontWeight.Normal,
//                    fontSize = 14.sp,
//                    letterSpacing = 0.25.sp
//                )
//            )
//        }

    }
}