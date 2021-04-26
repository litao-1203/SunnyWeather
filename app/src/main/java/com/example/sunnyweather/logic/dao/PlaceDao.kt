package com.example.sunnyweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.model.Place
import com.google.gson.Gson

object PlaceDao {
    //将Place对象存储到sharedPreferences文件中
    fun savePlace(place: Place){
        sharedPreferences().edit {
            //通过GSON将Place对象转成一个JSON字符串
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place{
        //将JSON字符串从SharedPreferences文件中读取出来
        val placeJson = sharedPreferences().getString("place", "")
        //通过GSON将JSON字符串解析成Place对象并返回
        return Gson().fromJson(placeJson, Place::class.java)
    }
    //判断是否有数据已被存储
    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
}