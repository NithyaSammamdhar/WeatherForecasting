package com.product.weatherforecasting.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey val city: String,
    val temp: Double,
    val description: String,
    val icon: String,
    val humidity: Int,
    val windSpeed: Double,
    val timestamp: Long
)
