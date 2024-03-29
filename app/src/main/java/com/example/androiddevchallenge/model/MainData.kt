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
package com.example.androiddevchallenge.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * MainData is the data model for main screen.
 *
 * @author PeytonWu
 * @since 2021/3/13
 */
@Parcelize
class MainData(var themes: List<Plant>, var plant: List<Plant>) : Parcelable

@Parcelize
class Plant(val name: String, val image: String, val description: String, var select: Boolean = false) :
    Parcelable
