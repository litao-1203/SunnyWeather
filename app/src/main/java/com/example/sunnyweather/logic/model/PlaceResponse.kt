package com.example.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName
//查询应答
data class PlaceResponse(val status: String, val places: List<Place>)
//@SerializedName()让JSON字段和Kotlin字段之间建立映射关系
data class Place(val name: String, val location: Location, @SerializedName("formatted_address") val address: String)
//位置信息，经纬度
data class Location(val lng: String, val lat: String)


