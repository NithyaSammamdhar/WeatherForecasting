package com.product.weatherforecasting.cache

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather WHERE city = :city LIMIT 1")
    fun getWeatherFlow(city: String): Flow<WeatherEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: WeatherEntity)
}
