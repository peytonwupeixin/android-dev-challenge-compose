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
package com.example.androiddevchallenge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.MainDestinations.USER_NAME_KEY
import com.example.androiddevchallenge.home.HomeScreen
import com.example.androiddevchallenge.log_in.LoginScreen
import com.example.androiddevchallenge.welcome.WelcomeScreen

/**
 * Destinations used in the challenge App.
 */
object MainDestinations {
    const val WELCOME_ROUTE = "welcome"
    const val LOG_IN_ROUTE = "log_in"
    const val HOME_ROUTE = "home"
    const val USER_NAME_KEY = "courseId"
}

@Composable
fun NavGraph(startDestination: String = MainDestinations.WELCOME_ROUTE) {
    val navController = rememberNavController()

    val actions = remember(navController) { MainActions(navController) }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.WELCOME_ROUTE) {
            WelcomeScreen(onLoginClick = actions.gotoLoginScreen)
        }
        composable(MainDestinations.LOG_IN_ROUTE) {
            LoginScreen(gotoHome = actions.gotoHome)
        }
        composable(
            "${MainDestinations.HOME_ROUTE}?$USER_NAME_KEY={$USER_NAME_KEY}",
            arguments = listOf(
                navArgument(USER_NAME_KEY) {
                    nullable = true
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val useName = arguments.getString(USER_NAME_KEY, "")
            HomeScreen(useName)
        }
    }
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val gotoLoginScreen: () -> Unit = {
        navController.navigate(MainDestinations.LOG_IN_ROUTE)
    }
    val gotoHome: (String, String) -> Unit = { userName: String, password: String ->
        navController.navigate("${MainDestinations.HOME_ROUTE}?$USER_NAME_KEY=$userName")
    }
}
