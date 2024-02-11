package com.example.applicationmeteo.service

import com.example.applicationmeteo.model.GeoCodeListResponse
import com.example.applicationmeteo.model.WeatherForecastResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v1/forecast")
    fun getWeatherForecast(
        @Query("latitude") latitude: Double = 50.95,
        @Query("longitude") longitude: Double = 1.85,
        @Query("current") current: String = "temperature_2m,relative_humidity_2m,rain,weather_code,wind_speed_10m",
        @Query("hourly") hourly: String = "temperature_2m,weather_code",
        @Query("daily") daily: String = "weather_code,temperature_2m_max,temperature_2m_min",
        @Query("timezone") timezone: String = "Europe/Berlin",
        @Query("wind_speed_unit") wind_speed_unit: String = "kmh",
        @Query("temperature_unit") tempreture_unit: String = "celsius"
    ): Call<WeatherForecastResponse>

    @GET("v1/search")
    fun getGeoLoc(
        @Query("name") name: String = "Calais",
        @Query("count") count: Int = 1,
        @Query("language") language: String = "fr",
        @Query("format") format: String = "json"
    ): Call<GeoCodeListResponse>
}
