package com.example.applicationmeteo.utils

import com.example.applicationmeteo.constant.mapToWeatherCategory
import com.example.applicationmeteo.model.WeatherModel
import com.example.applicationmeteo.model.HourlyData
import com.example.applicationmeteo.model.WeatherForecastResponse
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat

open class DataDAO {

    private var context: Context? = null
    private var locationManager: LocationManager? = null

    private var myLongitude: Double = 1.85
    private var myLatitude: Double = 50.95

    constructor(context: Context) {
        this.context = context
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager


        getLocation()
    }

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

    fun getMyLongitude(): Double {
        return this.myLongitude
    }

    fun getMyLatitude(): Double {
        return this.myLatitude
    }

    fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Handle if permissions are not granted
            return
        }

        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                // Handle location change
                val latitude = location.latitude
                val longitude = location.longitude
                // Do something with latitude and longitude
                myLongitude = longitude
                myLatitude = latitude
                // Don't forget to remove updates if needed
                locationManager?.removeUpdates(this)
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

            override fun onProviderEnabled(provider: String) {}

            override fun onProviderDisabled(provider: String) {}
        }

        // Register for location updates
        locationManager?.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null)
        locationManager?.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null)
    }
}