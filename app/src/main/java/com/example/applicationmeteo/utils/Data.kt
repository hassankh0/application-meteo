package com.example.applicationmeteo.utils

import com.example.applicationmeteo.constant.mapToWeatherCategory
import com.example.applicationmeteo.model.HomeModel
import com.example.applicationmeteo.model.HourlyData
import com.example.applicationmeteo.model.WeatherForecastResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

open class DataDAO {

    fun getHomePageData(weatherData: WeatherForecastResponse): HomeModel? {
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
                        dt.date,
                        dt.hour,
                        weatherData.hourly?.temperature2m?.get(i),
                        weatherData.hourly?.weatherCode?.get(i),
                        it
                    )
                }
                if (dataPoint != null) {
                    hourlyData.add(dataPoint)
                }
            }
        }


        val homeModel: HomeModel? =
            weatherData.current?.weatherCode?.let { mapToWeatherCategory(it) }?.let {
                weatherData?.current?.relativeHumidity2m?.toString()?.let { it1 ->
                    HomeModel(
                        it,
                        weatherData?.current?.temperature2m?.roundToInt().toString() +" "+ weatherData?.currentUnits?.temperature2m,
                        weatherData?.current?.rain?.roundToInt().toString() +" "+ weatherData?.currentUnits?.rain,
                        weatherData?.current?.windSpeed10m?.roundToInt().toString() +" "+ weatherData?.currentUnits?.windSpeed10m,
                        it1 +" "+ weatherData?.currentUnits?.relativeHumidity2m,
                        hourlyData
                    )
                }
            }

        return homeModel
    }

  /*  private fun getLocation() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }*/
}