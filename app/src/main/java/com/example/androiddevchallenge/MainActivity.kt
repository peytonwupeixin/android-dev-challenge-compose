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
package com.example.androiddevchallenge

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.androiddevchallenge.ui.theme.LocalElevations
import com.example.androiddevchallenge.ui.theme.LocalImages
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.utils.LocalBackDispatcher
import com.example.androiddevchallenge.utils.ProvideImageLoader
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

class MainActivity : AppCompatActivity() {

    private fun setBarTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.apply {
                systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This app draws behind the system bars, so we want to handle fitting system windows
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setBarTransparent()
        setContent {
            CompositionLocalProvider(LocalBackDispatcher provides onBackPressedDispatcher) {
                ProvideImageLoader {
                    MyTheme {
                        NavGraph()
                    }
                }
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    Surface(color = MaterialTheme.colors.background) {
        NavGraph()
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        NavGraph()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
