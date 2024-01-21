package com.example.applicationmeteo.constant

import com.example.applicationmeteo.R

enum class WeatherCategoryEnum(val description: String, val imageResourceId: Int) {
    ENSOLEILLE("Ensoleillé", R.drawable.sun),
    PLUVIEUX("Pluvieux", R.drawable.raining),
    ENNEIGE("Enneigé", R.drawable.snowflake),
    NUAGEUX("Nuageux", R.drawable.sun_cloud),
    ORAGEUX("Orageux", R.drawable.orage);

    companion object {
        fun valueOf(value: WeatherCategoryEnum): WeatherCategoryEnum {
            return values().firstOrNull { it.description.equals(value.description, ignoreCase = true) }
                ?: throw IllegalArgumentException("No enum constant for description: ${value.description}")
        }
    }
}

fun mapToWeatherCategory(code: Int): WeatherCategoryEnum {
    return when (code) {
        0 -> WeatherCategoryEnum.ENSOLEILLE // Clear sky
        in listOf(1, 2, 3, 48) -> WeatherCategoryEnum.NUAGEUX // Mainly clear, partly cloudy, and overcast
        in listOf(61, 63, 65, 66, 67, 80, 81, 82) -> WeatherCategoryEnum.PLUVIEUX // Rain and rain showers
        in listOf(71, 73, 75, 77, 85, 86) -> WeatherCategoryEnum.ENNEIGE // Snow fall and snow showers
        in listOf(95, 96, 99) -> WeatherCategoryEnum.ORAGEUX // Thunderstorms
        else -> WeatherCategoryEnum.ENSOLEILLE // Default to sunny
    }
}
