package com.example.applicationmeteo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationmeteo.MainActivity
import com.example.applicationmeteo.R
import com.example.applicationmeteo.adapter.WeatherAdapter
import com.example.applicationmeteo.constant.WeatherCategoryEnum
import com.example.applicationmeteo.model.HourlyData
import com.example.applicationmeteo.model.WeatherModel

class SearchFragment(
    private val context: MainActivity
) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val horizontalRecyclerView =
            view.findViewById<RecyclerView>(R.id.list_weather_search_result)
        val weatherList = arrayListOf(
            HourlyData(date = "", hour = "", temperature = 0.0, weatherCode = 1, temp_unit = "")
        )

        horizontalRecyclerView.adapter =
            WeatherAdapter(weatherList, R.layout.item_meteo_search_result)

        return view
    }
}