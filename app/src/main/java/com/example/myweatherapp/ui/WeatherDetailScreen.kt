package com.example.myweatherapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeatherDetailScreen(
    viewModel: WeatherViewModel,
    itemIndex: Int,
    modifier: Modifier = Modifier
) {
    val data by viewModel.weatherData.collectAsState()
    Text(
        // To do
        text = data[itemIndex],
        textAlign = TextAlign.Center,
        fontSize = 30.sp,
        modifier = modifier.padding(16.dp)
    )
}