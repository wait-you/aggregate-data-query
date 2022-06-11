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
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}