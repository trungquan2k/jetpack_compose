package com.example.jetrandomfood.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jetrandomfood.data.database.dao.FoodDao
import com.example.jetrandomfood.data.database.model.FoodDbModel
import com.example.jetrandomfood.domain.FoodModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class FoodRepository(private val foodDao: FoodDao) : Repository{

    val readAllData: LiveData<List<FoodDbModel>> = foodDao.getAllRooms()
    suspend fun addFood(item: List<FoodDbModel>) {
        foodDao.insert(item)
    }


    override fun getAllFood(): LiveData<List<FoodDbModel>> {
        TODO("Not yet implemented")
    }

    override fun insertFood(food: List<FoodDbModel>) {
        TODO("Not yet implemented")
    }


    private val foodDataLiveData: MutableLiveData<List<FoodDbModel>> by lazy {
        MutableLiveData<List<FoodDbModel>>()
    }
    override fun getRanDomFood(): MutableLiveData<List<FoodDbModel>> = foodDataLiveData



}