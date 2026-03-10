package com.example.myweatherapp

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                        val weatherData = listOf(
                            "Today - Storm 8 / 12",
                            "Tomorrow - Foggy 9 / 13",
                            "Thurs - Rainy 8 / 13",
                            "Fri - Foggy 8 / 12",
                            "Sat - Sunny 9 / 14",
                            "Sun - Sunny 10 / 15",
                            "Mon - Sunny 11 / 15"
                        )
                        NavHost(
                            navController = navController,
                            startDestination = AppScreens.Search.name
                        ) {
                            // call the composable() function once for each of the routes
                            composable(route = AppScreens.Search.name) { // Search
                                // TODO: Call the WeatherSearchScreen composable function
                                WeatherSearchScreen(weatherData, navController)
                            }
                            composable(
                                route = AppScreens.Detail.name + "/{index}", // Detail
                                arguments = listOf(
                                    navArgument(name = "index") {
                                        type = NavType.IntType // extract argument
                                    }
                                )
                            ) { index -> // call the weather search screen composable function
                                WeatherDetailScreen(
                                    weatherData = weatherData,
                                    itemIndex = index.arguments?.getInt("index")
                                        ?: 0 // passing the index allow null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class AppScreens{
    Search, //reference it everywhere else using AppScreens.Search.name
    Detail //reference it everywhere else using AppScreens.Detail.name
}


