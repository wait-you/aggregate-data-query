package cn.wenhe9.aggregatedata

import android.app.Application
import android.content.Context

/**
 *@author DuJinliang
 *2022/6/10
 */
class AggregateDataApplication : Application() {
    companion object {
        @SuppressWarnings("StaticFieldLeak")
        lateinit var context : Context

        const val SPRING_TRAVEL_KEY = "32714e93b309fda5bbc7f67b6fc93ea1"

        const val CONSTELLATION_KEY = "fba04a36c8f7d8af3f66d45bb32fab6c"

        const val WEATHER_KEY = "df2053ff6384681de9f8cdd983f68965"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}