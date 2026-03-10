package com.example.myweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.myweatherapp.ui.theme.MyWeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyWeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController: NavHostController = rememberNavController()
                        // dummy weather data
                        NavHost(
                            navController = navController,
                            startDestination = AppScreens.Search.name
                        ) {
                            // call the composable() function once for each of the routes
                            composable(route = AppScreens.Search.name) { // Search
                                // TODO: Call the WeatherSearchScreen composable function
                                WeatherSearchScreen(weatherData, navController)
                            }
                        }
                    }
                }
            }
        }
    }
}


/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyWeatherAppTheme {
        WeatherSearchScreen()
    }
}
 */