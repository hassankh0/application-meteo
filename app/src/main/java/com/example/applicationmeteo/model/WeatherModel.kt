package com.example.applicationmeteo.model

import com.example.applicationmeteo.constant.WeatherCategoryEnum

class WeatherModel(
    val category: WeatherCategoryEnum = WeatherCategoryEnum.ENNEIGE,
    val temperature: String = "25°",
    val pluie: String = "22",
    val vent: String = "12",
    val humidite: String = "18",
    val heure: String = "8am"
)
