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
 * 网络数据源访问入口
 */
object AggregateDataNetwork {

    private val springTravelService = ServiceCreator.create<SpringTravelService>()

    private val constellationService = ServiceCreator.create<ConstellationService>()

    private val weatherService = ServiceCreator.create<WeatherService>()

    /**
     * 异步查询天气信息
     */
    suspend fun getWeatherInfo(city: String) = weatherService.getWeatherInfo(city).await()

    /**
     * 异步查询星座信息
     */
    suspend fun getConstellationInfo(keyword: String) = constellationService.getConstellationInfo(keyword).await()

    /**
     * 异步查询星座列表
     */
    suspend fun getConstellationList() = constellationService.getConstellationList().await()

    /**
     * 异步查询城市信息
     */
    suspend fun getCities() = springTravelService.getCities().await()

    /**
     * 异步查询政策信息
     */
    suspend fun getPolicy(from: String, to: String) = springTravelService.getPolicy(from, to).await()

    /**
     * 拓展函数
     * 开启协程执行业务，并对返回体进行判断
     */
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