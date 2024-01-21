package com.example.applicationmeteo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationmeteo.R
import com.example.applicationmeteo.constant.WeatherCategoryEnum
import com.example.applicationmeteo.model.WeatherModel

class WeatherAdapter(
    private val weatherList: List<WeatherModel>,
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

        holder.weatherTemperature.text = currentWeather.temperature
        holder.weatherHour.text = currentWeather.heure

        val weatherCategoryEnum = currentWeather.category
        val imageResourceId = weatherCategoryEnum.imageResourceId
        holder.weatherCategory.setImageResource(imageResourceId)
    }
}
