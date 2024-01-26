package com.example.applicationmeteo.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.applicationmeteo.model.WeatherForecastResponse
import com.example.applicationmeteo.service.ApiConfig
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class MainViewModel() : ViewModel() {

    private val _weatherForecastData = MutableLiveData<WeatherForecastResponse>()
    val weatherForecastData: LiveData<WeatherForecastResponse> get() = _weatherForecastData

    private val _isLoading = MutableLiveData<Boolean>()

    private val _isError = MutableLiveData<Boolean>()

    var errorMessage: String = ""
        private set

    fun getForecastWeather(client: Call<WeatherForecastResponse>) {

        // Send API request using Retrofit
        client.enqueue(object : Callback<WeatherForecastResponse> {

            override fun onResponse(
                call: Call<WeatherForecastResponse>,
                response: Response<WeatherForecastResponse>
            ) {

                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null) {
                    onError("Data Processing Error")
                    return
                }

                _isLoading.value = false
                _weatherForecastData.postValue(responseBody)
            }

            override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
                onError(t.message)
                t.printStackTrace()
            }

        })
    }

    private fun onError(inputMessage: String?) {

        val message = if (inputMessage.isNullOrBlank() or inputMessage.isNullOrEmpty()) "Unknown Error"
        else inputMessage

        errorMessage = StringBuilder("ERROR: ")
            .append("$message some data may not displayed properly").toString()

        _isError.value = true
        _isLoading.value = false
    }

}