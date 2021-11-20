package com.example.jetrandomfood.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.jetrandomfood.data.database.model.FoodDbModel

@Dao
interface FoodDao {
    @Query("SELECT * FROM FoodDb ")
    fun getAllRooms(): LiveData<List<FoodDbModel>>


    @Query("SELECT * FROM FoodDb where id = :id")
    fun getById(id: Int): FoodDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE) //conflict the data
    suspend fun insert(item : List<FoodDbModel>)


    @Query("SELECT * FROM FoodDb ORDER BY RANDOM() LIMIT 1")
    fun getRandomFood(): LiveData<List<FoodDbModel>>

}