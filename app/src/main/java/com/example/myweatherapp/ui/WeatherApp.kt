package com.example.myweatherapp.ui

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myweatherapp.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp() {
    val viewModel: WeatherViewModel = viewModel()

    val context = LocalContext.current
    val navController: NavHostController = rememberNavController()

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            title = {
                Text("Weather App")
            },
            actions = {
                // TODO: Move the two buttons functions here
                IconButton(onClick = {/* TODO: */
                    val location = "London"
                    // construct the uri
                    val geoUri = Uri.parse("geo:0,0?q=$location")
                    Log.d("uri", location)

                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = geoUri
                    }

                    //if there is an app that can handle the implicit intent
                    if (intent.resolveActivity(context.packageManager) != null) {
                        Log.d("WeatherSearchScreen", "there is an app")
                        context.startActivity(intent)
                    } else {
                        Log.d("WeatherSearchScreen", "no app handling implicit intent")
                    }

                }) {
                    Icon(Icons.Filled.Place, contentDescription = "View location on Map")
                }
                IconButton(onClick = {/*TODO: */
                    Toast.makeText(
                        context,
                        "Refreshing Weather!",
                        Toast.LENGTH_LONG
                    ).show()

                    // fetch weather data
                    viewModel.fetchForecastForCity("London")
                    Log.d("WeatherSearchScreen", "Weather Refreshed!")
                }) {
                    Icon(Icons.Filled.Search, contentDescription = "Refresh Weather")
                }
                IconButton(onClick = { /* TODO: */ }) {
                    Icon(Icons.Filled.Settings, contentDescription = "Configure the default city")
                }
            }
        )
    }) { innerPadding ->
        //add a NavHost
        NavHost(
            navController = navController,
            startDestination = AppScreens.Search.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            //call the composable() function once for each of the routes
            composable(route = AppScreens.Search.name) {//Search
                //call the WeatherSearchScreen composable function
                WeatherSearchScreen(viewModel, navController)
            }
            composable(
                route = AppScreens.Detail.name + "/{index}",//Detail
                arguments = listOf(
                    navArgument(name = "index") {
                        type = NavType.IntType //extract the argument
                    }
                )
            ) { index ->//call the WeatherDetailScreen composable function
                WeatherDetailScreen(
                    viewModel,
                    itemIndex = index.arguments?.getInt("index") ?: 0//passing the index
                )
            }
            composable(
                route = AppScreens.Setting.name
            ) {
                WeatherSettingScreen(settingViewModel)
            }
        }
    }
}

enum class AppScreens {
    Search, //reference it everywhere else using AppScreens.Search.name
    Detail, //reference it everywhere else using AppScreens.Detail.name
    Setting
}