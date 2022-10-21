package com.example.diplomski_android

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        val context: Context = MyApplication.applicationContext()
    }
}