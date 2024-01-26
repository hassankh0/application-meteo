package com.example.applicationmeteo.model

import com.example.applicationmeteo.constant.WeatherCategoryEnum

class HomeModel(
    val category: WeatherCategoryEnum = WeatherCategoryEnum.ENNEIGE,
    val temperature: Any? = "25°",
    val pluie: String = "22",
    val vent: String = "12",
    val humidite: String = "18",
    val journee: List<HourlyData>
)