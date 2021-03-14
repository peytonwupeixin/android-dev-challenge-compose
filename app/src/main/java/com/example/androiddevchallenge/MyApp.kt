package com.example.androiddevchallenge

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
/*
* Custom Application class
*
*  @author PeytonWu
 * @since 2021/3/13
* */
class MyApplication : Application() {

    companion object {
        /**
         * Global application context.
         */
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
