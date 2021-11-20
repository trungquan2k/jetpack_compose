package com.example.jetrandomfood.ui.component

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.jetrandomfood.data.database.model.FoodDbModel

import com.example.jetrandomfood.R
import com.example.jetrandomfood.viewmodel.MainViewModel
import com.example.jetrandomfood.viewmodel.MainViewModelFactory


val insertFoodsData = listOf(
    FoodDbModel(1, "Bánh mì", "150000", R.drawable.banhmi),
    FoodDbModel(2, "Bánh bao", "120000", R.drawable.banhbao),
    FoodDbModel(3, "Bún bò", "140000", R.drawable.bunbo),
    FoodDbModel(4, "Bún rêu", "50000", R.drawable.bunreu),
    FoodDbModel(5, "Mỳ quảng", "500000", R.drawable.miquang),
    FoodDbModel(6, "Cơm tấm", "30000", R.drawable.comtam),
    FoodDbModel(7, "Hủ tiếu", "120000", R.drawable.hutieu),
    FoodDbModel(8, "Phở", "100000", R.drawable.pho),
    FoodDbModel(9, "Xôi xéo", "90000", R.drawable.xoi),
    FoodDbModel(10, "Sườn xào chua ngọt", "125000", R.drawable.suon_xao),
    FoodDbModel(11, "Gà", "90000", R.drawable.ga),
    FoodDbModel(12, "Bánh cuốn", "125000", R.drawable.banhcuong),
)


@Composable
fun ListDataRoom(food: FoodDbModel) {
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
                food.price,
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
fun ListFoodsItem() {
    val context = LocalContext.current
    val roomViewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(context.applicationContext as Application)
    )
    roomViewModel.addFood(insertFoodsData)
    val getAllRecord = roomViewModel.readAllData.observeAsState(listOf()).value // fetching the data
    Column() {
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier.padding(10.dp)
        ) {
            items(getAllRecord.size) { index ->
                ListDataRoom(getAllRecord[index])
            }
        }
    }
}









