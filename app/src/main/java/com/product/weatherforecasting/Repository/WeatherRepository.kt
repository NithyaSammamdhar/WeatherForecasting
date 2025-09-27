package com.product.weatherforecasting.Repository

import com.product.weatherforecasting.WeatherApi
import com.product.weatherforecasting.cache.WeatherDao
import com.product.weatherforecasting.cache.WeatherEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(val entity: WeatherEntity) : WeatherState()
    data class Error(val message: String) : WeatherState()
}

class WeatherRepository(
    private val api: WeatherApi,
    private val dao: WeatherDao,
    private val apiKey: String
) {
    fun observeCached(city: String): Flow<WeatherEntity?> = dao.getWeatherFlow(city)

    suspend fun fetch(city: String): Flow<WeatherState> = flow {
        emit(WeatherState.Loading)
        try {
            val resp = api.getWeatherByCity(city, apiKey, "metric")
            // map to entity
            val weather = resp.weather.firstOrNull()
            val entity = WeatherEntity(
                city = resp.name,
                temp = resp.main.temp,
                description = weather?.description ?: "",
                icon = weather?.icon ?: "",
                humidity = resp.main.humidity,
                windSpeed = resp.wind.speed,
                timestamp = resp.dt
            )

            // store cache
            withContext(Dispatchers.IO) { dao.insert(entity) }

            emit(WeatherState.Success(entity))
        } catch (e: Exception) {
            // try cached
            val cached = dao.getWeatherFlow(city) // returns Flow<WeatherEntity?>
            val cachedVal = cached.first()
            if (cachedVal != null) {
                emit(WeatherState.Success(cachedVal))
            } else {
                emit(WeatherState.Error(e.localizedMessage ?: "Unknown error"))
            }
        }
    }
}
