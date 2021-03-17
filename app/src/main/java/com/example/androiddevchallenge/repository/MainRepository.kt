/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.repository

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.androiddevchallenge.MyApplication
import com.example.androiddevchallenge.common.Resource
import com.example.androiddevchallenge.model.MainData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
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
    suspend fun getMainData(): Resource<MainData> = dataHelper.getMainData()
}

class DataHelper {

    /**
     * Read the data from local and parse it into model object to return.
     */
    suspend fun getMainData(): Resource<MainData> = coroutineScope {
        var result = Resource.error<MainData>("data parse fail")
        launch {
            try {
                val assetsManager = MyApplication.context.assets
                val inputReader = InputStreamReader(assetsManager.open("maindata_local.json"))
                val jsonString = BufferedReader(inputReader).readText()
                val typeOf = object : TypeToken<MainData>() {}.type
                result = Resource.success(Gson().fromJson(jsonString, typeOf))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.join()
        result
    }
}

private val repository = MainRepository()
internal val LocalRepository = staticCompositionLocalOf { MainRepository() }

@Composable
fun ProviderLocalRepository(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalRepository provides repository) {
        content()
    }
}
