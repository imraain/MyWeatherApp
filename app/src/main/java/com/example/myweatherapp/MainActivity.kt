package com.example.myweatherapp

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myweatherapp.ui.WeatherDetailScreen
import com.example.myweatherapp.ui.WeatherSearchScreen
import com.example.myweatherapp.ui.WeatherViewModel
import com.example.myweatherapp.ui.theme.MyWeatherAppTheme

class MainActivity : ComponentActivity() {
    private val br: BroadcastReceiver = MyPowerReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyWeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(//Let Surface take care of the innerPadding in Scaffold, place the WeatherSearchScreen in the correct place, and use theme colours, etc.
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController: NavHostController = rememberNavController()

                        val viewModel: WeatherViewModel = viewModel()
                        NavHost(
                            navController = navController,
                            startDestination = AppScreens.Search.name
                        ) {
                            //call the composable() function once for each of the routes
                            composable(route = AppScreens.Search.name) {//Search
                                //To do: call the WeatherSearchScreen composable function
                                WeatherSearchScreen(viewModel, navController)
                            }
                            composable(
                                route = AppScreens.Detail.name + "/{index}",//Detail
                                arguments = listOf(
                                    navArgument(name = "index") {
                                        type = NavType.IntType //extract the argument
                                    })
                            ) { index ->//call the WeatherSearchScreen composable function
                                WeatherDetailScreen(
                                    viewModel,
                                    itemIndex = index.arguments?.getInt("index")
                                        ?: 0//passing the index
                                )
                            }

                        }

                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //create an instance of IntentFilter
        val filter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        //set the receiver exported flag if your app is listening to system broadcast
        val receiverFlag = ContextCompat.RECEIVER_EXPORTED
        //register the receiver in the Activity context
        ContextCompat.registerReceiver(this, br, filter, receiverFlag)
    }

    override fun onPause() {
        super.onPause()
        //unregister the broadcast receiver to avoid leaking the receiver outside the Activity context
        unregisterReceiver(br)
    }
}

enum class AppScreens {
    Search, //reference it everywhere else using AppScreens.Search.name
    Detail //reference it everywhere else using AppScreens.Detail.name
}



