package com.example.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**提供一种获取全局Context的方式
 * 每当应用程序启动的时候，系统就会自动初始化Application类
 */
class SunnyWeatherApplication: Application() {

    //定义Context静态变量
    companion object{
        //配置彩云天气的令牌值
        const val TOKEN = "JV3Vloin4bs1CwRO"
        //忽略内存泄漏的警告
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}