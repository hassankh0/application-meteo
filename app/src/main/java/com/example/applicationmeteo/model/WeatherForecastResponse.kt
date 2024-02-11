package com.example.applicationmeteo.model

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(

	@field:SerializedName("elevation")
	val elevation: Any? = null,

	@field:SerializedName("daily_units")
	val dailyUnits: DailyUnits? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("latitude")
	val latitude: Any? = null,

	@field:SerializedName("hourly_units")
	val hourlyUnits: HourlyUnits? = null,

	@field:SerializedName("generationtime_ms")
	val generationtimeMs: Any? = null,

	@field:SerializedName("current")
	val current: Current? = null,

	@field:SerializedName("timezone_abbreviation")
	val timezoneAbbreviation: String? = null,

	@field:SerializedName("current_units")
	val currentUnits: CurrentUnits? = null,

	@field:SerializedName("daily")
	val daily: Daily? = null,

	@field:SerializedName("utc_offset_seconds")
	val utcOffsetSeconds: Int? = null,

	@field:SerializedName("hourly")
	val hourly: Hourly? = null,

	@field:SerializedName("longitude")
	val longitude: Any? = null
)

data class DailyUnits(

	@field:SerializedName("apparent_temperature_min")
	val apparentTemperatureMin: String? = null,

	@field:SerializedName("apparent_temperature_max")
	val apparentTemperatureMax: String? = null,

	@field:SerializedName("temperature_2m_max")
	val temperature2mMax: String? = null,

	@field:SerializedName("temperature_2m_min")
	val temperature2mMin: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("weather_code")
	val weatherCode: String? = null
)

data class Current(

	@field:SerializedName("wind_speed_10m")
	val windSpeed10m: Double? = null,

	@field:SerializedName("temperature_2m")
	val temperature2m: Double? = null,

	@field:SerializedName("rain")
	val rain: Double? = null,

	@field:SerializedName("relative_humidity_2m")
	val relativeHumidity2m: Int? = null,

	@field:SerializedName("is_day")
	val isDay: Int? = null,

	@field:SerializedName("interval")
	val interval: Int? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("weather_code")
	val weatherCode: Int? = null
)

data class Daily(

	@field:SerializedName("temperature_2m_max")
	val temperature2mMax: List<Double?>? = null,

	@field:SerializedName("temperature_2m_min")
	val temperature2mMin: List<Double?>? = null,

	@field:SerializedName("time")
	val time: List<String?>? = null,

	@field:SerializedName("weather_code")
	val weatherCode: List<Int?>? = null
)

data class CurrentUnits(

	@field:SerializedName("wind_speed_10m")
	val windSpeed10m: String? = null,

	@field:SerializedName("temperature_2m")
	val temperature2m: String? = null,

	@field:SerializedName("rain")
	val rain: String? = null,

	@field:SerializedName("relative_humidity_2m")
	val relativeHumidity2m: String? = null,

	@field:SerializedName("apparent_temperature")
	val apparentTemperature: String? = null,

	@field:SerializedName("is_day")
	val isDay: String? = null,

	@field:SerializedName("interval")
	val interval: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("weather_code")
	val weatherCode: String? = null
)

data class HourlyUnits(

	@field:SerializedName("wind_speed_10m")
	val windSpeed10m: String? = null,

	@field:SerializedName("temperature_2m")
	val temperature2m: String? = null,

	@field:SerializedName("rain")
	val rain: String? = null,

	@field:SerializedName("relative_humidity_2m")
	val relativeHumidity2m: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("weather_code")
	val weatherCode: String? = null
)

data class Hourly(

	@field:SerializedName("wind_speed_10m")
	val windSpeed10m: List<Double?>? = null,

	@field:SerializedName("temperature_2m")
	val temperature2m: List<Double?>? = null,

	@field:SerializedName("rain")
	val rain: List<Double?>? = null,

	@field:SerializedName("relative_humidity_2m")
	val relativeHumidity2m: List<Int?>? = null,

	@field:SerializedName("time")
	val time: List<String?>? = null,

	@field:SerializedName("weather_code")
	val weatherCode: List<Int?>? = null
)

data class HourlyData(
	val date: String = "",
	val hour: String? = "",
	val temperature: Double? = 0.0,
	val temperatureMin: Double? = 0.0,
	val temperatureMax: Double? = 0.0,
	val weatherCode: Int? = 0,
	val temp_unit: String? = "",
	val ville: String? = ""
)

