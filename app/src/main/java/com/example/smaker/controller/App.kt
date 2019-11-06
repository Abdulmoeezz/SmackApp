package com.example.smaker.controller

import android.app.Application
import android.content.SharedPreferences
import com.example.smaker.utilities.SharedPrefs

class App:Application() {

    companion object{
    lateinit var  sharedPreferences: SharedPrefs
    }

    override fun onCreate() {
        sharedPreferences= SharedPrefs(applicationContext)
        super.onCreate()
    }




}