package cn.wenhe9.aggregatedata.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 *@author DuJinliang
 *2022/6/10
 */
object AggregateDataNetwork {
    private val springTravelService = ServiceCreator.create<SpringTravelService>()

    private val constellationService = ServiceCreator.create<ConstellationService>()

    private val weatherService = ServiceCreator.create<WeatherService>()

    suspend fun getWeatherInfo(city: String) = weatherService.getWeatherInfo(city).await()

    suspend fun getConstellationInfo(keyword: String) = constellationService.getConstellationInfo(keyword).await()

    suspend fun getConstellationList() = constellationService.getConstellationList().await()

    suspend fun getCities() = springTravelService.getCities().await()

    suspend fun getPolicy(from: String, to: String) = springTravelService.getPolicy(from, to).await()

    private suspend fun <T> Call<T>.await() : T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()

                    if (body != null){
                        continuation.resume(body)
                    }else {
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }
}