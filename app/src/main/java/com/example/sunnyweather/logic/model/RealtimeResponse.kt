package com.example.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName
//获取实时天气信息
data class RealtimeResponse(val status: String, val result: Result){

    data class Result(val realtime: Realtime)
    //天气信息，包括天气状况、温度、空气质量
    data class Realtime(val skycon: String, val temperature: Float,
                @SerializedName("air_quality") val airQuality: AirQuality)

    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)
}
