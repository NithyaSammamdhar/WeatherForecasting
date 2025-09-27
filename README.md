# 🌦 Weather Forecasting App (Kotlin, MVVM)

A simple weather forecasting Android app built using **Kotlin**, **MVVM architecture**, **Retrofit**, **Coroutines**, and **Room Database**.  
It fetches real-time weather data from the [OpenWeatherMap API](https://openweathermap.org/api).

## 🚀 Features
- Search weather by city name 🌍
- Live weather updates using **OpenWeatherMap**
- **MVVM architecture** with `ViewModel`, `LiveData` / `Flow`
- Offline caching using **Room Database**
- Weather icons loaded with **Glide**
- Material UI with **ViewBinding**

## 🛠 Tech Stack
- **Language:** Kotlin
- **Architecture:** MVVM + Repository pattern
- **Network:** Retrofit2 + OkHttp3 (logging)
- **Async:** Kotlin Coroutines + Flow
- **Database:** Room
- **UI:** Material Components + ViewBinding

## 📷 Screenshots
(Add your app screenshots here)

## 🔑 API Key Setup
1. Get a free API key from [OpenWeatherMap](https://home.openweathermap.org/users/sign_up).  
2. Add your key to `local.properties` (not committed to GitHub):
