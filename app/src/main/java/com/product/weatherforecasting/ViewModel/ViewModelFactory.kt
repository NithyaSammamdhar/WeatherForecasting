package com.product.weatherforecasting.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.product.weatherforecasting.Repository.WeatherRepository

class WeatherViewModelFactory(private val repo: WeatherRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(repo) as T
    }
}
