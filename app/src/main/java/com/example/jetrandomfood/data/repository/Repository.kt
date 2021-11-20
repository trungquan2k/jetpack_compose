package com.example.jetrandomfood.data.repository

import androidx.lifecycle.LiveData
import com.example.jetrandomfood.data.database.model.FoodDbModel
import com.example.jetrandomfood.domain.FoodModel

interface Repository {

    fun getAllFood(): LiveData<List<FoodDbModel>>

    fun insertFood(food: List<FoodDbModel>)


    fun getRanDomFood(): LiveData<List<FoodDbModel>>

}