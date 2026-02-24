package com.example.myweatherapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myweatherapp.ui.theme.MyWeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyWeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WeatherSearchScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherSearchScreen(modifier: Modifier = Modifier) {
    var location by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    // create a state for the forecast list
    val forecastList = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Welcome Message
        Text(
            modifier = Modifier
                .padding(bottom = 16.dp),
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
            }, label = {
                Text(stringResource(R.string.location_msg))
            })
        // Refresh button
        Button(
            modifier = Modifier
                .align(Alignment.End),
            onClick = {
                Toast.makeText(
                    context,
                    "Refreshing Weather!",
                    Toast.LENGTH_LONG
                ).show()

                // clear forecast before refresh
                forecastList.clear()

                // Forecast list shows after refresh
                forecastList.addAll(
                    listOf(
                        "Today - Storm 8 / 12",
                        "Tomorrow - Foggy 9 / 13",
                        "Thurs - Rainy 8 / 13",
                        "Fri - Foggy 8 / 12",
                        "Sat - Sunny 9 / 14",
                        "Sun - Sunny 10 / 15",
                        "Mon - Sunny 11 / 15"
                    )
                )

                Log.d("WeatherSearchScreen", "button clicked")
            })
        {
            Text(stringResource(R.string.refresh))
        }

        // display forecast list using lazycolumns
        LazyColumn {
            items(forecastList) {
                forecast ->
                Text(text = forecast)
            }
        }

    }
}

// Composable function to show a list of dates and weather on refresh
@Composable
fun WeatherList(data: List<String>, modifier: Modifier) {
    val weatherList = listOf(
        "Today - Storm 8 / 12",
        "Tomorrow - Foggy 9 / 13",
        "Thurs - Rainy 8 / 13",
        "Fri - foggy 8 / 12",
        "Sat -Sunny 9 / 14",
        "Sun - Sunny 10 / 15",
        "Mon - Sunny 11 / 15"
    )
    // List showing weather after button refresh
    LazyColumn {
        items(items = data) { item ->
        }
//        item { Text(text = "Today - Storm 8 / 12") }
//        item { Text(text = "Tomorrow - Foggy 9 / 13") }
//        item { Text(text = "Thursday - Rainy 8 / 13") }
//        item { Text(text = "Fri - foggy 8 / 12") }
//        item { Text(text = "Sat - Sunny 9 / 14") }
//        item { Text(text = "Sun - Sunny 10 / 15") }
//        item { Text(text = "Mon - Sunny 11 / 15") }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyWeatherAppTheme {
        WeatherSearchScreen()
    }
}