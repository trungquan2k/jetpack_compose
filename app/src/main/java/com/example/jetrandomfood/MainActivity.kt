package com.example.jetrandomfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable

import com.example.jetrandomfood.appdrawer.HomePageScreen
import com.example.jetrandomfood.routing.JetFoodRouter
import com.example.jetrandomfood.routing.Screen

import com.example.jetrandomfood.ui.screen.ListRanDomFood
import com.example.jetrandomfood.ui.theme.JetrandomfoodTheme
import com.example.jetrandomfood.viewmodel.MainViewModel
import com.example.jetrandomfood.viewmodel.MainViewModelFactory


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels(factoryProducer = {
        MainViewModelFactory(
            application)
    })
    @ExperimentalMaterialApi
    override fun onCreate(savedObjectState: Bundle?) {
        super.onCreate(savedObjectState)
        setContent {
            JetrandomfoodTheme() {
                MainActivityScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
@ExperimentalMaterialApi
private fun MainActivityScreen(viewModel: MainViewModel){
    Surface{
        when(JetFoodRouter.currentScreen){
            is Screen.RanDomFood -> ListRanDomFood(viewModel)
            is Screen.Home -> HomePageScreen(viewModel)
        }
    }
}