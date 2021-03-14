package com.example.androiddevchallenge.repository

import com.example.androiddevchallenge.MyApplication
import com.example.androiddevchallenge.model.MainData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Repository to communicate with MainViewModel and DataHelper.
 *
 * @author PeytonWu
 * @since 2021/3/13
 */
class MainRepository {

    private val dataHelper: DataHelper = DataHelper()
    /**
     * Get the data.
     */
    suspend fun getMainData() = withContext(Dispatchers.IO) {
        dataHelper.getMainData()
    }
}

class DataHelper{
    /**
     * Read the data from local and parse it into model object to return.
     */
    suspend fun getMainData():MainData= coroutineScope{
        var mainData:MainData = MainData()
        launch {
            try {
                val assetsManager = MyApplication.context.assets
                val inputReader = InputStreamReader(assetsManager.open("maindata.json"))
                val jsonString = BufferedReader(inputReader).readText()
                val typeOf = object : TypeToken<MainData>() {}.type
                mainData = Gson().fromJson(jsonString, typeOf)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.join()
        mainData
    }
}