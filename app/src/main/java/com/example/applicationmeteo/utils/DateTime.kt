package com.example.applicationmeteo.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class DateTimeComponents(val date: String, val hour: String)

fun parseDateTime(input: String): DateTimeComponents? {
    try {
        val dateTime = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
        val date = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val hour = dateTime.format(DateTimeFormatter.ofPattern("h a"))
        return DateTimeComponents(date, hour)
    } catch (e: DateTimeParseException) {
        // Handle the case where parsing fails
        println("Error parsing date and time: $e")
        return null
    }
}