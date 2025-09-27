package com.product.weatherforecasting.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.product.weatherforecasting.Repository.WeatherRepository
import com.product.weatherforecasting.Repository.WeatherState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repo: WeatherRepository) : ViewModel() {

    private val _state = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val state: StateFlow<WeatherState> = _state

    fun fetch(city: String) {
        viewModelScope.launch {
            repo.fetch(city).collect { st ->
                _state.value = st
            }
        }
    }

    // expose cached flow for UI (optional)
    fun observeCached(city: String) = repo.observeCached(city)
}
