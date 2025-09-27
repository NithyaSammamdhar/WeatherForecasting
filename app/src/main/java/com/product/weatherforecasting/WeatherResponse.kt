package com.product.weatherforecasting

data class WeatherResponse(
    val name: String,
    val dt: Long,
    val main: Main,
    val weather: List<WeatherItem>,
    val wind: Wind,
    val sys: Sys?
)

data class Main(
    val temp: Double,
    val humidity: Int
)

data class WeatherItem(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double
)

data class Sys(
    val country: String?
)
