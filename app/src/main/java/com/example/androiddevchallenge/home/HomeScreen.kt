package com.example.androiddevchallenge.home

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
@Composable
fun HomeScreen(userName:String){
    Row{
        Text(text = "Home->${userName}")
    }
}