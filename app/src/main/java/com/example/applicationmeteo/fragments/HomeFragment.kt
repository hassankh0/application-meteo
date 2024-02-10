package com.example.applicationmeteo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationmeteo.MainActivity
import com.example.applicationmeteo.R
import com.example.applicationmeteo.adapter.WeatherAdapter
import com.example.applicationmeteo.constant.WeatherCategoryEnum
import com.example.applicationmeteo.constant.mapToWeatherCategory
import com.example.applicationmeteo.model.WeatherForecastResponse
import com.example.applicationmeteo.model.WeatherModel
import com.example.applicationmeteo.service.ApiConfig
import com.example.applicationmeteo.utils.DataDAO
import com.example.applicationmeteo.utils.getCurrentDate
import com.example.applicationmeteo.viewmodel.MainViewModel
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment(
    private val context: MainActivity
) : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private var dataDao: DataDAO = DataDAO(context)

    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainViewModel = MainViewModel()
        subscribe()

        view = inflater.inflate(R.layout.fragment_home, container, false)

        mainViewModel.getForecastWeather(ApiConfig.getApiService().getWeatherForecast(latitude = dataDao.getMyLatitude(), longitude = dataDao.getMyLongitude(), tempreture_unit = this.context.getDegreeTemp(), wind_speed_unit = this.context.getDegreeVent()));

        // Get the current date TextView
        val dateTextView: TextView = view.findViewById(R.id.fragment_home_date)


        // Get the current date
        val currentDate = getCurrentDate()

        // Set the current date to the TextView
        dateTextView.text = currentDate

        val weekTextView: TextView = view.findViewById(R.id.weekText)
        weekTextView.setOnClickListener {
            // Navigate to WeekFragment
            val transaction: FragmentTransaction = context.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.page_container, WeekFragment(context))
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return view
    }

    private fun subscribe() {
        mainViewModel.weatherForecastData.observe(context) {weatherFData ->
            // Display weather data to the UI
            setResult(weatherFData)
        }
    }

    private fun setResult(weatherData: WeatherForecastResponse) {
        val data = dataDao.getHomePageData(weatherData)

        val currentTemperature: TextView = view.findViewById(R.id.fragment_home_temprature)
        currentTemperature.text = data?.temperature.toString()

        val currentPluie: TextView = view.findViewById(R.id.fragment_home_pluie)
        currentPluie.text = data?.pluie.toString()

        val currentVent: TextView = view.findViewById(R.id.fragment_home_vent)
        currentVent.text = data?.vent.toString()

        val currentHumidite: TextView = view.findViewById(R.id.fragment_home_humidite)
        currentHumidite.text = data?.humidite.toString()

        val currentTemps: TextView = view.findViewById(R.id.fragment_home_temps)
        currentTemps.text = data?.category?.description

        val currentTempsImage: ImageView = view.findViewById(R.id.fragment_home_temp_image)
        val imageResourceId = data?.category?.imageResourceId
        if (imageResourceId != null) {
            currentTempsImage.setImageResource(imageResourceId)
        }
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.list_weather)
        horizontalRecyclerView.adapter = data?.journee?.let { WeatherAdapter(it, R.layout.item_meteo_list) }



    }



}
