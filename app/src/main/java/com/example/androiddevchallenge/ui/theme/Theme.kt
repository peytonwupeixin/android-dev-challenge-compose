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
package com.example.androiddevchallenge.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R

private val DarkColorPalette = darkColors(
    primary = green900,
    secondary = green300,
    background = Gray,
    surface = Color(red = 1f, green = 1f, blue = 1f, alpha = 0.15f),
    onPrimary = Color.White,
    onSecondary = Gray,
    onBackground = Color.White,
    onSurface = Color(red = 1f, green = 1f, blue = 1f, alpha = 0.85f)
)

private val LightColorPalette = lightColors(
    primary = pink100,
    secondary = pink900,
    background = Color.White,
    surface = Color(red = 1f, green = 1f, blue = 1f, alpha = 0.85f),
    onPrimary = Gray,
    onSecondary = Color.White,
    onBackground = Gray,
    onSurface = Gray
)

@Composable
fun MyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val images = if (darkTheme) DarkImages else LightImages
    val localCustomerColor = if (darkTheme) DarkCustomerColor else LightCustomerColor
    CompositionLocalProvider(
        LocalElevations provides elevation,
        LocalImages provides images,
        LocalCustomerColor provides localCustomerColor
    ) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}
@Immutable
data class CustomerColor(val logIn:Color)
private val DarkCustomerColor = CustomerColor(Color.White)
private val LightCustomerColor = CustomerColor(pink900)
internal val LocalCustomerColor = staticCompositionLocalOf<CustomerColor> {
    error("No LocalImages specified")
}

@Immutable
data class Elevations(val card: Dp = 1.dp, val snackBar: Dp = 2.dp, val bottomNavigaiton: Dp = 16.dp)
private val elevation = Elevations()
internal val LocalElevations = staticCompositionLocalOf { Elevations() }


@Immutable
data class Images(@DrawableRes val logo: Int,@DrawableRes val welcomeBg: Int,@DrawableRes val welcomeIllos: Int)
private val DarkImages = Images(R.drawable.ic_dark_logo,R.drawable.ic_dark_welcome_bg,R.drawable.ic_dark_welcome_illos)
private val LightImages = Images(R.drawable.ic_light_logo,R.drawable.ic_light_welcome_bg,R.drawable.ic_light_welcome_illos)
internal val LocalImages = staticCompositionLocalOf<Images> {
    error("No LocalImages specified")
}
