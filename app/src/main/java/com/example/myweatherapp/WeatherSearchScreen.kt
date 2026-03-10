package com.example.myweatherapp

import android.R.attr.data
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun WeatherSearchScreen(
    weatherData: List<String>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // var text by rememberSavable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") } // variable to keep value of TextField
    val context = LocalContext.current // get the activity context within a composable function

    // create a state for the forecast list
    var iniData by remember { mutableStateOf(emptyList<String>()) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Welcome Message
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = stringResource(R.string.welcome_msg),
        )
        // Location Text Field
        TextField(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.Start)
                .fillMaxWidth(),
            value = location,
            onValueChange = {
                location = it
            },
            label = {
                Text(stringResource(R.string.location_msg))
            })
        // Refresh button
        Button(
            modifier = Modifier.align(Alignment.End), onClick = {

                iniData = weatherData

                Toast.makeText(
                    context, "Refreshing Weather!", Toast.LENGTH_LONG
                ).show()
                Log.d("WeatherSearchScreen", "button clicked")
            }) {
            Text(stringResource(R.string.refresh))
        }
        WeatherList(iniData, navController, modifier)
    }
}

@Composable
fun WeatherList(
    data: List<String>,
    navController: NavHostController,
    modifier: Modifier
) {
    // display forecast list using LazyColumns
    val context = LocalContext.current // get the activity within the composable function

    LazyColumn {
        items(data) {
            // iterate through each item in the list
                item ->
            Text(
                text = item, modifier = modifier
                    .padding(16.dp)
                    .clickable(
                        onClick = {
                            Toast.makeText(
                                context, "Item Clicked!", Toast.LENGTH_LONG
                            ).show()
                        }
                    )
            )
        }
    }
}
