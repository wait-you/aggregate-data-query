package cn.wenhe9.aggregatedata.logic.network

import cn.wenhe9.aggregatedata.AggregateDataApplication
import cn.wenhe9.aggregatedata.logic.model.springTravel.city.CityResponse
import cn.wenhe9.aggregatedata.logic.model.springTravel.policy.PolicyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *@author DuJinliang
 *2022/6/10
 */
interface SpringTravelService {
    @GET("citys?key=${AggregateDataApplication.SPRING_TRAVEL_KEY}")
    fun getCities() : Call<CityResponse>

    @GET("query?key=${AggregateDataApplication.SPRING_TRAVEL_KEY}")
    fun getPolicy(@Query("from") from: String, @Query("to")to: String) : Call<PolicyResponse>
}