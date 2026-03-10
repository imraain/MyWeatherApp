import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.myweatherapp.R

fun weatherSearchScreen(
    weatherData: List<String>,
    navController: navHostController,
    modifier: Modifier = Modifier
) {

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

        // display forecast list using LazyColumns
        LazyColumn {
            items(forecastList)
            {
                    forecast ->
                Text(text = forecast,
                    modifier = modifier.padding(16.dp).clickable(
                        onClick = {
                            Toast.makeText(context, "Item Clicked", Toast.LENGTH_SHORT).show()
                        }
                    )
                )
            }
        }

    }
}