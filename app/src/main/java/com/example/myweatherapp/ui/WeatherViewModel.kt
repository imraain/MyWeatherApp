package com.example.myweatherapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import javax.net.ssl.HttpsURLConnection
import kotlin.math.roundToInt

class WeatherViewModel : ViewModel() {
    //stage 1: the ui state is a JSON string
    private val _uiState = MutableStateFlow("")

    //Publicly exposed as a read-only StateFlow
    val uiState = _uiState.asStateFlow()

    //stage 2: now the ui state is a list of Strings
    //private mutable _weatherData with an initial empty list of Strings, backing property of weatherData
    //weatherData will be fetched from the Internet using Kotlin coroutines, use State Flow as it works seamlessly with coroutines
    private val _weatherData = MutableStateFlow<List<String>>(emptyList())
    val weatherData = _weatherData.asStateFlow()

    //use your own apiKey below
    val apiKey = BuildConfig.apiKeyWeather

    // Function to fetch and update the forecast data
    fun fetchForecastForCity(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val (lat, lon) = getCoordinates(city)
                Log.d("launch coordinates", "$lat, $lon")
                val url2 =
                    "https://api.openweathermap.org/data/2.5/forecast?lat=$lat&lon=$lon&units=metric&appid=$apiKey"
                Log.d("launch url2", url2)
                val result = getJSONFromApi(url2) // Your suspend function to fetch data
                _uiState.value = result//stage1: result JSON string

                _weatherData.value =
                    getWeatherDataFromJson(result) //stage 2: Parse the JSON string into the list of Strings

            } catch (e: Exception) {
                // Handle exceptions
                Log.d("launch", "exception")
                e.printStackTrace()
            }

        }
    }

    //The helper function that converts city name to co-ordinates
    private fun getCoordinates(city: String): Pair<String, String> {
        //build the URL using the user entered city
        val url1 = "https://api.openweathermap.org/geo/1.0/direct?q=$city&appid=$apiKey"
        Log.d("getCoordinates url1", url1)
        var lat = ""
        var lon = ""
        try {
            val response = getJSONFromApi(url1)
            Log.d("getCoordinates", response)
            val cityArray = JSONArray(response)
            val coordinates = cityArray.getJSONObject(0)
            lat = coordinates.getDouble("lat").toString()
            Log.d("getCoordinates", lat)
            lon = coordinates.getDouble("lon").toString()
            Log.d("getCoordinates", lon)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Pair(lat, lon)
    }

    // Send a GET request to the URL, fetch weather forecast, returns the response in JSON string
    private fun getJSONFromApi(url: String): String {
        var result = ""
        var conn: HttpsURLConnection? = null
        try {
            val request = URL(url)
            conn = request.openConnection() as HttpsURLConnection
            Log.d("suspend function", "create a connection")
            conn.connect()
            Log.d("suspend function", "connect")
            val inStrea: InputStream = conn.inputStream
            result = convertInputStreamToString(inStrea)
            Log.d("suspend function", result)
        } catch (e: Exception) {
            Log.d("suspend function", "exception")
            result = "Network Error! Please check network connection."
            e.printStackTrace()
        } finally {

            conn?.disconnect()
        }
        return result //returns the fetched JSON string
    }

    // The helper function that converts the input stream to String
    @Throws(IOException::class)
    private fun convertInputStreamToString(inS: InputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inS))
        val result = StringBuilder()
        var line: String?

        // Read out the input stream buffer line by line until it's empty
        while (bufferedReader.readLine().also { line = it } != null) {
            result.append(line)
        }
        Log.d("convert", result.toString())

        // Close the input stream and return
        inS.close()
        return result.toString()
    }

    //Take the raw json string and pull out the data we need, and construct a list of Strings with them
    private fun getWeatherDataFromJson(jsonStr: String): List<String> {
        val resultList = mutableListOf<String>()

        try {
            val owmList = "list"
            val owmDateTime = "dt"
            val owmMain = "main"
            val owmTemp = "temp"
            val owmWeather = "weather"
            val owmDescription = "description"

            val forecastJson = JSONObject(jsonStr)
            val weatherArray: JSONArray = forecastJson.getJSONArray(owmList)

            for (i in 0 until weatherArray.length()) {

                val eachForecast = weatherArray.getJSONObject(i)

                val dateTime = eachForecast.getLong(owmDateTime)
                val dayHour = getReadableDateString(dateTime)

                val mainObject = eachForecast.getJSONObject(owmMain)
                val temp = mainObject.getDouble(owmTemp).roundToInt()

                val weatherObject =
                    eachForecast.getJSONArray(owmWeather).getJSONObject(0)
                val description =
                    weatherObject.getString(owmDescription)

                val formatted =
                    "$dayHour - $description - $temp°C"

                resultList.add(formatted)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }

        return resultList
    }

    private fun getReadableDateString(time: Long): String {
        val date = Date(time * 1000)
        val format = SimpleDateFormat("E, MMM d, ha")
        return format.format(date)
    }

    private fun formatHighLows(high: Double, low: Double): String {
        val roundedHigh = Math.round(high)
        val roundedLow = Math.round(low)
        return "$roundedHigh/$roundedLow"
    }
}