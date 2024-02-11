package com.example.applicationmeteo.adapter

import android.content.Context
import android.content.SharedPreferences
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import java.util.Objects
import kotlin.math.roundToInt

class WeatherAdapter(
    private var weatherList: List<HourlyData>,
    private val layoutId: Int,
    private val context: Context
) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("weather_data", Context.MODE_PRIVATE)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val weatherCategory = view.findViewById<ImageView>(R.id.item_meteo_list_category)
        val weatherTemperature = view.findViewById<TextView>(R.id.item_meteo_list_temperature)
        val weatherHour = view.findViewById<TextView>(R.id.item_meteo_list_hour)

        val weatherAllDayCategory = view.findViewById<ImageView>(R.id.item_meteo_allday_list_image)
        val weatherAllDayDay = view.findViewById<TextView>(R.id.item_meteo_allday_list_day)
        val weatherAllDayTempMax= view.findViewById<TextView>(R.id.item_meteo_allday_list_tempMax)
        val weatherAllDayTempMin = view.findViewById<TextView>(R.id.item_meteo_allday_list_tempMin)
        val weatherAllDaytemp = view.findViewById<TextView>(R.id.item_meteo_allday_list_temps)

        val searchweatherTemp = view.findViewById<TextView>(R.id.search_temp)
        val searchweatherTemperature = view.findViewById<TextView>(R.id.search_temperature)
        val searchweatherhb = view.findViewById<TextView>(R.id.search_hb)
        val searchweatherVille = view.findViewById<TextView>(R.id.search_ville_name)

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

        if (Objects.nonNull(holder.weatherAllDayCategory)) {
            val weatherCategoryEnum = currentWeather.weatherCode?.let { mapToWeatherCategory(it) }
            val imageResourceId = weatherCategoryEnum?.imageResourceId
            if (imageResourceId != null) {
                holder.weatherAllDayCategory.setImageResource(imageResourceId)
            }
        }
        if (Objects.nonNull(holder.weatherAllDaytemp)) {
            val weatherCategoryEnum = currentWeather.weatherCode?.let { mapToWeatherCategory(it) }
            val desciptionResourceId = weatherCategoryEnum?.description
            holder.weatherAllDaytemp.text = desciptionResourceId
        }

        if (Objects.nonNull(holder.weatherAllDayDay)) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(currentWeather.date, formatter)

            val dayName = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.FRENCH)

            holder.weatherAllDayDay.text = dayName.take(3).toUpperCase()
        }

        if (Objects.nonNull(holder.weatherAllDayTempMax)) {

            holder.weatherAllDayTempMax.text = currentWeather.temperatureMax?.roundToInt().toString() + "°"
        }

        if (Objects.nonNull(holder.weatherAllDayTempMin)) {

            holder.weatherAllDayTempMin.text = currentWeather.temperatureMin?.roundToInt().toString() + "°"
        }

        if (Objects.nonNull(holder.searchweatherTemp)) {
            val weatherCategoryEnum = currentWeather.weatherCode?.let { mapToWeatherCategory(it) }
            val desciptionResourceId = weatherCategoryEnum?.description
            holder.searchweatherTemp.text = desciptionResourceId
        }

        if (Objects.nonNull(holder.searchweatherTemperature)) {

            holder.searchweatherTemperature.text = currentWeather.temperature?.roundToInt().toString()  +" "+ currentWeather.temp_unit
        }

        if (Objects.nonNull(holder.searchweatherVille)) {

            holder.searchweatherVille.text = "ll"
        }

        if (Objects.nonNull(holder.searchweatherhb)) {

            holder.searchweatherhb.text = ""
        }


    }

    fun addData(newHourlyData: HourlyData) {
        val mutableList = weatherList.toMutableList()
        mutableList.add(newHourlyData)
        weatherList = mutableList
        notifyItemInserted(mutableList.size - 1)
        saveWeatherDataToCache(weatherList)
    }

    private fun saveWeatherDataToCache(weatherList: List<HourlyData>) {
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(weatherList)
        editor.putString("weather_list", json)
        editor.apply()
    }

    fun getWeatherDataFromCache(): List<HourlyData> {
        val json = sharedPreferences.getString("weather_list", null)
        val type = object : TypeToken<MutableList<HourlyData>>() {}.type
        return if (json != null) {
            Gson().fromJson(json, type)
        } else {
            mutableListOf()
        }
    }
}