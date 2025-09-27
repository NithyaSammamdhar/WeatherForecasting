package com.product.weatherforecasting

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.product.weatherforecasting.Repository.WeatherRepository
import com.product.weatherforecasting.Repository.WeatherState
import com.product.weatherforecasting.ViewModel.WeatherViewModel
import com.product.weatherforecasting.ViewModel.WeatherViewModelFactory
import com.product.weatherforecasting.cache.AppDatabase
import com.product.weatherforecasting.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repo: WeatherRepository
    private lateinit var viewModelFactory: WeatherViewModelFactory
    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize API, Database, DAO
        val api = NetworkModule.create()
        val db = AppDatabase.getInstance(applicationContext)
        val dao = db.weatherDao()
        val apiKey = BuildConfig.OPEN_WEATHER_API_KEY

        repo = WeatherRepository(api, dao, apiKey)
        viewModelFactory = WeatherViewModelFactory(repo)
        viewModel = ViewModelProvider(this, viewModelFactory)[WeatherViewModel::class.java]

        binding.btnSearch.setOnClickListener {
            val city = binding.etCity.text.toString().trim()
            if (city.isNotEmpty()) {
                viewModel.fetch(city)
                observeState()
            }
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.state.collect { st ->
                when (st) {
                    is WeatherState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.tvTemp.text = ""
                        binding.tvDescription.text = ""
                        binding.tvExtra.text = ""
                        binding.ivIcon.setImageResource(android.R.drawable.ic_menu_report_image)
                    }
                    is WeatherState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val e = st.entity
                        binding.tvTemp.text = "${e.temp}°C — ${e.city}"
                        binding.tvDescription.text = e.description.replaceFirstChar { it.uppercaseChar() }
                        binding.tvExtra.text = "Humidity: ${e.humidity}%  Wind: ${e.windSpeed} m/s"

                        val iconUrl = "https://openweathermap.org/img/wn/${e.icon}@2x.png"
                        Glide.with(this@MainActivity)
                            .load(iconUrl)
                            .placeholder(android.R.drawable.ic_menu_report_image)
                            .into(binding.ivIcon)
                    }
                    is WeatherState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvTemp.text = ""
                        binding.tvDescription.text = "Error: ${st.message}"
                        binding.tvExtra.text = ""
                        binding.ivIcon.setImageResource(android.R.drawable.ic_menu_report_image)
                    }
                }
            }
        }
    }
}
