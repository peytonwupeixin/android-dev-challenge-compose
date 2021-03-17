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
package com.example.androiddevchallenge.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.common.Resource
import com.example.androiddevchallenge.home.HomeTabs.Cart
import com.example.androiddevchallenge.model.MainData
import com.example.androiddevchallenge.model.Plant
import com.example.androiddevchallenge.repository.LocalRepository
import com.example.androiddevchallenge.ui.theme.LocalElevations
import com.example.androiddevchallenge.utils.getDrawble
import com.example.androiddevchallenge.utils.produceState
import com.example.androiddevchallenge.utils.textCenter
import dev.chrisbanes.accompanist.insets.navigationBarsHeight
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

@Composable
fun HomeScreen(userName: String) {
    val (selectedTab, setSelectedTab) = remember { mutableStateOf(HomeTabs.HOME) }
    val tabs = HomeTabs.values()
    val resource by produceState(LocalRepository.current, Unit) {
        getMainData()
    }
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        val unselectColor = MaterialTheme.colors.onPrimary.copy(alpha = LocalContentAlpha.current)
        Scaffold(
            backgroundColor = MaterialTheme.colors.background,
            bottomBar = {
                BottomNavigation(
                    backgroundColor = MaterialTheme.colors.primary,
                    modifier = Modifier.navigationBarsHeight(additional = 56.dp),
                    elevation = LocalElevations.current.bottomNavigaiton
                ) {
                    tabs.forEach { tab ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    painterResource(tab.icon),
                                    contentDescription = null,
                                    tint = if (tab == selectedTab) MaterialTheme.colors.onPrimary else unselectColor
                                )
                            },
                            label = {
                                Text(
                                    stringResource(tab.title),
                                    color = if (tab == selectedTab) MaterialTheme.colors.onPrimary else unselectColor
                                )
                            },
                            selected = tab == selectedTab,
                            onClick = {
                                setSelectedTab(tab)
                            },
                            alwaysShowLabel = true,
                            selectedContentColor = MaterialTheme.colors.onPrimary,
                            unselectedContentColor = LocalContentColor.current,
                            modifier = Modifier.navigationBarsPadding()
                        )
                    }
                }
            }
        ) { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            when (selectedTab) {
                HomeTabs.HOME -> HomeTabScrren(resource, modifier)
                Cart, HomeTabs.FAVORITES, HomeTabs.PROFILE -> UnSupportTabScreen(resource, modifier)
            }
        }
    }
}

@Composable
fun UnSupportTabScreen(resource: Resource<MainData>, modifier: Modifier) {
    Text(text = "Feature unsupported.")
}

@Composable
fun HomeTabScrren(resource: Resource<MainData>, modifier: Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopSearchSection()
        if (resource.data == null) {
            Text(text = "Data error")
        } else {
            LazyColumn(modifier = modifier) {
                item { Themes(resource.data.themes) }
                item { GardenTitle() }
                for (plant in resource.data.plant) {
                    item { GardenItem(plant) }
                }
            }
        }
    }
}

@Composable
fun GardenItem(plant: Plant) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(start = 16.dp, bottom = 8.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(shape = MaterialTheme.shapes.small, elevation = 0.dp) {
                Image(
                    painter = painterResource(getDrawble(plant.image)),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(64.dp)
                        .fillMaxHeight()
                )
            }
            Column(
                modifier = Modifier
                    .weight(1.0f)
                    .fillMaxHeight()

            ) {
                Text(
                    text = plant.name,
                    modifier = Modifier
                        .paddingFromBaseline(16.dp)
                        .padding(start = 16.dp),
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.h2,

                )
                Text(
                    text = plant.description,
                    modifier = Modifier
                        .weight(1.0f)
                        .paddingFromBaseline(16.dp)
                        .padding(start = 16.dp),
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.body1,

                )
            }
            val checkedState = remember { mutableStateOf(false) }
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                },
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(24.dp),
                colors = CheckboxDefaults.colors(checkmarkColor = MaterialTheme.colors.background)
            )
        }
        Divider(
            color = MaterialTheme.colors.onPrimary.copy(alpha = 0.2f),
            modifier = Modifier.padding(start = 72.dp, end = 16.dp)
        )
    }
}

@Composable
fun GardenTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "Design your home garden",
            modifier = Modifier
                .paddingFromBaseline(40.dp)
                .weight(1.0f),
            style = MaterialTheme.typography.h1, color = MaterialTheme.colors.onPrimary
        )
        Icon(
            painterResource(R.drawable.ic_filter_list),
            contentDescription = null,
            tint = MaterialTheme.colors.onPrimary,
        )
    }
}

@Composable
fun Themes(themes: List<Plant>) {
    Column {
        ThemeTitle()
        LazyRow(modifier = Modifier.padding(start = 0.dp)) {
            items(themes) { theme ->
                ThemeCard(theme, Modifier.padding(start = 16.dp))
            }
        }
    }
}

@Composable
fun ThemeCard(plant: Plant, modifier: Modifier) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier.size(136.dp, 136.dp),
        backgroundColor = MaterialTheme.colors.background,
        elevation = LocalElevations.current.card
    ) {
        Column {
            Image(
                painter = painterResource(getDrawble(plant.image)),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(96.dp)
                    .fillMaxWidth()
            )
            Text(
                text = plant.name,
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h2,

            )
        }
    }
}

@Composable
fun ThemeTitle() {
    Text(
        text = stringResource(id = R.string.browse_themes),
        style = MaterialTheme.typography.h1,
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier
            .paddingFromBaseline(top = 32.dp, bottom = 8.dp)
            .padding(start = 16.dp)
    )
}

@Composable
fun TopSearchSection() {
    OutlinedTextField(
        value = "", onValueChange = {},
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(R.drawable.ic_search), // Icons.Default.Search
                        contentDescription = stringResource(id = R.string.search),
                        tint = MaterialTheme.colors.onPrimary.copy(alpha = 0.5f),
                    )
                    Text(
                        text = stringResource(id = R.string.search),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onPrimary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.textCenter()
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, start = 16.dp, end = 16.dp)
            .height(56.dp),
    )
}

private enum class HomeTabs(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {

    HOME(R.string.home, R.drawable.ic_home),
    FAVORITES(R.string.favorites, R.drawable.ic_favorite_border),
    PROFILE(R.string.profile, R.drawable.ic_account_circle),
    Cart(R.string.cart, R.drawable.ic_shopping_cart)
}
