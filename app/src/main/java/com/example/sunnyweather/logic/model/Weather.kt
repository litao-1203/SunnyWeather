package com.example.sunnyweather.logic.model
//将Realtime和Daily对象封装
data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)
