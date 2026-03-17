package com.example.myweatherapp.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myweatherapp.R


@Composable
fun WeatherSearchScreen(
    viewModel: WeatherViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var text by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    val weatherData by viewModel.weatherData.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = stringResource(R.string.welcome_msg),
        )

        TextField(
            value = text,
            label = { Text(stringResource(R.string.location_msg)) },
            maxLines = 1,
            onValueChange = { text = it },
            modifier = modifier
                .padding(bottom = 16.dp)//add padding between child elements
                .fillMaxWidth(1f)//make the TextField fill the width of the screen
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {}
        Text(
            text = "Weather forecast for $text:",
            modifier = modifier.padding(bottom = 16.dp)
        )
        WeatherList(weatherData, navController, modifier)
    }
}


@Composable
fun WeatherList(
    data: List<String>,
    navController: NavHostController,
    modifier: Modifier
) {//create a lazy list of texts from the dummy data
    val context = LocalContext.current //get the activity context within a composable function
    LazyColumn {
        itemsIndexed(data) {//iterate through each item in the List and create a Text for each item
                index, item ->
            Text(
                text = item,
                modifier = modifier
                    .padding(16.dp)
                    .clickable(
                        onClick = { //handle the onClick event to the list item
                            Toast.makeText(context, "Item Clicked!", Toast.LENGTH_LONG).show()
                            navController.navigate(route = "Detail/$index")
                        }
                    )
            )//add padding between items in the lazy list

        }
    }
}
