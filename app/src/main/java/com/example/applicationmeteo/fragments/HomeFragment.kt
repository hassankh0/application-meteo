package com.example.applicationmeteo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationmeteo.R
import com.example.applicationmeteo.adapter.WeatherAdapter

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home , container, false)

        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.list_weather)
        horizontalRecyclerView.adapter = WeatherAdapter(R.layout.item_meteo_list)
 
    return view
    }

}