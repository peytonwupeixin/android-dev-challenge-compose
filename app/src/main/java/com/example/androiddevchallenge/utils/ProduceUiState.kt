/*
 * Copyright 2020 The Android Open Source Project
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
package com.example.androiddevchallenge.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import com.example.androiddevchallenge.common.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Launch a coroutine to create refreshable [UiState] from a suspending producer.
 *
 * [Producer] is any object that has a suspending method that returns [Result]. In the [block] call
 * the suspending method that produces a single value. The result of this call will be returned
 * along with an event to refresh (or call [block] again), and another event to clear error results.
 *
 * It is intended that you destructure the return at the call site. Here is an example usage that
 * calls dataSource.loadData(resourceId) and then displays a UI based on the result.
 *
 * ```
 * val (result, onRefresh, onClearError) = produceUiState(dataSource, resourceId) {
 *     loadData(resourceId)
 * }
 * Text(result.value)
 * Button(onClick = onRefresh) { Text("Refresh" }
 * Button(onClick = onClearError) { Text("Clear loading error") }
 * ```
 *
 * Repeated calls to onRefresh are conflated while a request is in progress.
 *
 * @param producer the data source to load data from
 * @param key any argument used by production lambda, such as a resource ID
 * @param block suspending lambda that produces a single value from the data source
 * @return data state, onRefresh event, and onClearError event
 */
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun <Producer, T> produceState(
    producer: Producer,
    key: Any?,
    block: suspend Producer.() -> Resource<T>
): State<Resource<T>> {
    val result = produceState(Resource.loading<T>(), producer, key) {
        value = producer.block()
    }
    return result
}
