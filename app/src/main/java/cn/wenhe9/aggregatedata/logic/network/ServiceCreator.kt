package cn.wenhe9.aggregatedata.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *@author DuJinliang
 *2022/6/10
 */
object ServiceCreator {
    private const val BASE_URL = "http://192.168.101.105:8080/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass : Class<T>) : T = retrofit.create(serviceClass)

    inline fun<reified  T> create() : T = create(T::class.java)
}