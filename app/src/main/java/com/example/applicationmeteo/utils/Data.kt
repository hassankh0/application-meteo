package com.example.applicationmeteo.utils

import com.example.applicationmeteo.constant.mapToWeatherCategory
import com.example.applicationmeteo.model.WeatherModel
import com.example.applicationmeteo.model.HourlyData
import com.example.applicationmeteo.model.WeatherForecastResponse
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

open class DataDAO {

    fun getHomePageData(weatherData: WeatherForecastResponse): WeatherModel? {
        val hourlyData = arrayListOf<HourlyData>()


        val now = LocalDateTime.now()

        for (i in weatherData.hourly?.time?.indices!!) {
            // Creating HourlyData objects and adding to the list
            val dt: DateTimeComponents? = weatherData.hourly?.time[i]?.let { parseDateTime(it) }

            if (dt != null && dt.date == now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) &&
                now.isBefore(
                    LocalDateTime.parse(
                        weatherData.hourly?.time[i],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
                    )
                )
            ) {
                val dataPoint = weatherData.currentUnits?.temperature2m?.let {
                    HourlyData(
                        date = dt.date,
                        hour = dt.hour,
                        temperature = weatherData.hourly?.temperature2m?.get(i),
                        weatherCode = weatherData.hourly?.weatherCode?.get(i),
                        temp_unit = it
                    )
                }
                if (dataPoint != null) {
                    hourlyData.add(dataPoint)
                }
            }
        }

        val weatherModel: WeatherModel? =
            weatherData.current?.weatherCode?.let { mapToWeatherCategory(it) }?.let {
                weatherData?.current?.relativeHumidity2m?.toString()?.let { it1 ->
                    WeatherModel(
                        category = it,
                        temperature = weatherData?.current?.temperature2m?.roundToInt().toString() +" "+ weatherData?.currentUnits?.temperature2m,
                        pluie = weatherData?.current?.rain?.roundToInt().toString() +" "+ weatherData?.currentUnits?.rain,
                        vent = weatherData?.current?.windSpeed10m?.roundToInt().toString() +" "+ weatherData?.currentUnits?.windSpeed10m,
                        humidite = it1 +" "+ weatherData?.currentUnits?.relativeHumidity2m,
                        journee = hourlyData
                    )
                }
            }

        return weatherModel
    }

    fun getWeekPageData(weatherData: WeatherForecastResponse): WeatherModel? {
        val dailyWeatherData = mutableListOf<HourlyData>()

        val now = LocalDate.now()

        for (i in weatherData.daily?.time?.indices!!) {
            val date: LocalDate? = weatherData.daily?.time[i]?.let {
                LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            }

            if (date != null && date.isAfter(now) && date.isBefore(now.plusDays(8))) {
                val dataPoint = weatherData.currentUnits?.temperature2m?.let {
                    HourlyData(
                        date = date.toString(),
                        temperatureMin =  weatherData.daily?.temperature2mMin?.get(i),
                        temperatureMax = weatherData.daily?.temperature2mMax?.get(i),
                        weatherCode = weatherData.hourly?.weatherCode?.get(i),
                        temp_unit = it
                    )
                }
                if (dataPoint != null) {
                    dailyWeatherData.add(dataPoint)
                }
            }
        }

        val currentWeather = weatherData.current
        val weatherModel: WeatherModel? = currentWeather?.weatherCode?.let { mapToWeatherCategory(it) }?.let {
            currentWeather.relativeHumidity2m?.toString()?.let { humidity ->
                WeatherModel(
                    category =  it,
                    temperature = currentWeather.temperature2m?.roundToInt().toString() + " " + weatherData.currentUnits?.temperature2m,
                    pluie = currentWeather.rain?.roundToInt().toString() + " " + weatherData.currentUnits?.rain,
                    vent = currentWeather.windSpeed10m?.roundToInt().toString() + " " + weatherData.currentUnits?.windSpeed10m,
                    humidite = humidity + " " + weatherData.currentUnits?.relativeHumidity2m,
                    journee = dailyWeatherData
                )
            }
        }

        return weatherModel
    }

    /*  private fun getLocation() {
          val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
          if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
              ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
          }
          locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
      }*/
}