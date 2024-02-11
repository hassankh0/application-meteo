package com.example.applicationmeteo.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationmeteo.MainActivity
import com.example.applicationmeteo.R
import com.example.applicationmeteo.adapter.WeatherAdapter
import com.example.applicationmeteo.model.HourlyData
import com.example.applicationmeteo.service.ApiConfig
import com.example.applicationmeteo.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class SearchFragment(
    private val context: MainActivity
) : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var searchEditText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var ville: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = MainViewModel()
        subscribe()
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        searchEditText = view.findViewById(R.id.searchEditText)
        recyclerView = view.findViewById(R.id.list_weather_search_result)
        weatherAdapter = WeatherAdapter(ArrayList(), R.layout.item_meteo_search_result, context)
        setupRecyclerView()
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mainViewModel.getGeoCode(ApiConfig.getApiServiceGeo().getGeoLoc(searchEditText.text.toString()));
                clearSearchTextAndHideKeyboard()
                true
            } else {
                false
            }
        }
        return view
    }

    private fun subscribe() {
        mainViewModel.geoCodeData.observe(context) { data ->
            if (data.results.isNullOrEmpty()) {
                Snackbar.make(requireView(), "La ville recherchée n'a pas été trouver", Snackbar.LENGTH_LONG).show()
            } else {
                data.results[0]?.longitude?.let { data.results[0]?.latitude?.let { it1 ->
                    performSearch(it, it1)
                } }
            }
        }
        mainViewModel.weatherForecastData.observe(context) { weatherFData ->
            val date = weatherFData.current?.time
            val hour = "12:00 PM"
            val temperature = weatherFData.current?.temperature2m
            val weatherCode = weatherFData.current?.weatherCode
            val tempUnit = weatherFData.currentUnits?.temperature2m
            val hourlyData = date?.let {
                HourlyData(
                    date = it,
                    hour = hour,
                    temperature = temperature,
                    weatherCode = weatherCode,
                    temp_unit = tempUnit,
                    ville = ville
                )
            }
            if (hourlyData != null) {
                weatherAdapter.addData(hourlyData)
            }
        }
    }

    private fun setupRecyclerView() {
        val weatherList = weatherAdapter.getWeatherDataFromCache()
        weatherAdapter = WeatherAdapter(weatherList, R.layout.item_meteo_search_result, context)
        recyclerView.adapter = weatherAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedItem = weatherAdapter.removeItem(position)
                Snackbar.make(requireView(), "Item deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        weatherAdapter.restoreItem(position, deletedItem)
                    }.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun performSearch(longitude: Double, latitude: Double) {
        mainViewModel.getForecastWeather(ApiConfig.getApiService().getWeatherForecast(
            latitude = latitude,
            longitude = longitude,
            tempreture_unit = this.context.getDegreeTemp(),
            wind_speed_unit = this.context.getDegreeVent()
        ))
    }

    private fun clearSearchTextAndHideKeyboard() {
        ville = searchEditText.text.toString()
        searchEditText.text.clear()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
