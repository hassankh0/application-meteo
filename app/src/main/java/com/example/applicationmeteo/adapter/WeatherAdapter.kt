package com.example.applicationmeteo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationmeteo.R
import com.example.applicationmeteo.constant.WeatherCategoryEnum
import com.example.applicationmeteo.constant.mapToWeatherCategory
import com.example.applicationmeteo.model.HourlyData
import com.example.applicationmeteo.model.WeatherModel
import java.util.Objects
import kotlin.math.roundToInt

class WeatherAdapter(
    private val weatherList: List<HourlyData>,
    private val layoutId: Int
) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val weatherCategory = view.findViewById<ImageView>(R.id.item_meteo_list_category)
        val weatherTemperature = view.findViewById<TextView>(R.id.item_meteo_list_temperature)
        val weatherHour = view.findViewById<TextView>(R.id.item_meteo_list_hour)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = weatherList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentWeather = weatherList[position]

        if (Objects.nonNull(holder.weatherTemperature)){
        holder.weatherTemperature.text = currentWeather.temperature?.roundToInt().toString() +" "+ currentWeather.temp_unit
            }
        if (Objects.nonNull(holder.weatherHour)) {
            holder.weatherHour.text = currentWeather.hour
        }
        if (Objects.nonNull(holder.weatherCategory)) {
            val weatherCategoryEnum = currentWeather.weatherCode?.let { mapToWeatherCategory(it) }
            val imageResourceId = weatherCategoryEnum?.imageResourceId
            if (imageResourceId != null) {
                holder.weatherCategory.setImageResource(imageResourceId)
            }
        }
    }
}
