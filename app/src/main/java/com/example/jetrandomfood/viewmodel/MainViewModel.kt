package com.example.jetrandomfood.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.jetrandomfood.data.database.AppDatabase
import com.example.jetrandomfood.data.database.model.FoodDbModel
import com.example.jetrandomfood.data.repository.FoodRepository

import com.example.jetrandomfood.data.repository.Repository
import com.example.jetrandomfood.domain.FoodModel
import com.example.jetrandomfood.routing.JetFoodRouter
import com.example.jetrandomfood.routing.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application): AndroidViewModel(application) {


    val readAllData: LiveData<List<FoodDbModel>>
    private val repository: FoodRepository

    init {
        val foodDao = AppDatabase.getInstance(application).foodDao()
        repository = FoodRepository(foodDao = foodDao)
        readAllData = repository.readAllData
    }

    fun addFood(item: List<FoodDbModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFood(item = item)
        }
    }


    fun onClickRanDomFood(){
        viewModelScope.launch(Dispatchers.Default) {
            repository.getRanDomFood()
            withContext(Dispatchers.Main) {
                JetFoodRouter.navigateTo(Screen.RanDomFood)
            }
        }
    }

    fun onClickBackHome(){
        viewModelScope.launch(Dispatchers.Default) {
            repository.getRanDomFood()
            withContext(Dispatchers.Main) {
                JetFoodRouter.navigateTo(Screen.Home)
            }
        }
    }



}