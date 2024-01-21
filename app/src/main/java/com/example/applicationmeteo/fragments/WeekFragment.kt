package com.example.applicationmeteo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationmeteo.R
import com.example.applicationmeteo.adapter.WeatherAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class WeekFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meto_allday , container, false)
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.list_weather_allday)
        horizontalRecyclerView.adapter = WeatherAdapter(R.layout.item_meto_allday_list)
        // Get the current date TextView
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)

        // Get the current date
        val currentDate = getCurrentDate()

        // Set the current date to the TextView
        dateTextView.text = currentDate

        return view
    }
    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val currentDate = calendar.time

        val dateFormat = SimpleDateFormat("EEE MMM dd", Locale.getDefault())
        return dateFormat.format(currentDate)
    }
}