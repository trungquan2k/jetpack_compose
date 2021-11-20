package com.example.jetrandomfood.viewmodel


import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.jetrandomfood.data.repository.Repository



class MainViewModelFactory(
  private val application: Application
): ViewModelProvider.Factory {
  override fun <T: ViewModel?> create(modelClass: Class<T>): T {
    @Suppress("UNCHECKED_CAST")
    if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
      return MainViewModel(application) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}