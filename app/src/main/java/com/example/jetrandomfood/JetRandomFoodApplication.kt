package com.example.jetrandomfood

import android.app.Application
import com.example.jetrandomfood.dependencyinjection.DependencyInjector

class JetRandomFoodApplication : Application(){

    lateinit var dependencyInjector: DependencyInjector

    override fun onCreate() {
        super.onCreate()
        initDependencyInjector()
    }

    private fun initDependencyInjector() {
        dependencyInjector = DependencyInjector(this)
    }
}