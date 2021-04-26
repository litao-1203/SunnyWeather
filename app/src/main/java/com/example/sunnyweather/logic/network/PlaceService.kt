package com.example.sunnyweather.logic.network

import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
//定义一个用于访问彩云天气城市搜索API的Retrofit接口
interface PlaceService {
    //声明一个@Get注解，当调用searchPlaces()方法时，Retrofit会自动发起一条GET请求去访问@GET注解中配置的地址
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    //searchPlaces()方法的返回值被声明为Call<PlaceResponse>,返回的JSON数据会自动解析为PlaceResponse对象
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}