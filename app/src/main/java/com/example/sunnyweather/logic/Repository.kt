package com.example.sunnyweather.logic

import androidx.lifecycle.liveData
import com.example.sunnyweather.logic.dao.PlaceDao
import com.example.sunnyweather.logic.model.Place
import com.example.sunnyweather.logic.model.Weather
import com.example.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * 仓库层
 * 发起网络请求去获取最新的数据，并将获得的数据返回给调用方
 */
object Repository {
    //liveData()会自动构建并返回一个LiveData对象，并在其代码块提供一个挂起函数的上下文；
    //由于Android不允许在主线程中进行网络请求，故将线程参数类型指定为Dispatchers.IO，进行线程转换
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok"){
            val places = placeResponse.places
            //包装获取的城市数据列表
            Result.success(places)
        }else{
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }
    //率刷新天气，获取实时天气信息和未来天气信息
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO){
            coroutineScope {
                val deferredRealtime = async {
                    SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
                }
                val deferredDaily = async {
                    SunnyWeatherNetwork.getDailyWeather(lng, lat)
                }
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()
                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
                    //如果获取实时天气和未来天气状态都"OK"，则将Realtime和Daily对象取出并封装到一个Weather对象中
                    val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                    Result.success(weather)
                }else{
                    Result.failure(
                            RuntimeException(
                                    "realtime response status is ${realtimeResponse.status}" +
                                            "daily response status is ${dailyResponse.status}"
                            )
                    )
                }
            }
        }

    private fun <T> fire(context: CoroutineContext, block: suspend  () -> Result<T>) = liveData(context){
       val result = try {
           block()
       } catch (e: Exception){
           Result.failure<T>(e)
       }
        emit(result)
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}