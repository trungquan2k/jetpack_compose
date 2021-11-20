package com.example.jetrandomfood.dependencyinjection

import android.content.Context
import androidx.room.Room

import com.example.jetrandomfood.data.database.AppDatabase
import com.example.jetrandomfood.data.repository.FoodRepository
import com.example.jetrandomfood.data.repository.Repository

/**
 * Provides dependencies across the app.
 */
class DependencyInjector(applicationContext: Context) {

  private val database: AppDatabase by lazy { provideDatabase(applicationContext) }

  private fun provideDatabase(applicationContext: Context): AppDatabase =
    Room.databaseBuilder(
      applicationContext,
      AppDatabase::class.java,
      "FoodDb"
    ).build()


}
