package cn.wenhe9.aggregatedata.logic.network

import cn.wenhe9.aggregatedata.AggregateDataApplication
import cn.wenhe9.aggregatedata.logic.model.constellation.ConstellationResponse
import cn.wenhe9.aggregatedata.logic.model.springTravel.city.CityResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *@author DuJinliang
 *2022/6/12
 * 星座 api
 */
interface ConstellationService {
    /**
     * 查询星座列表
     */
    @GET("constellation/list")
    fun getConstellationList() : Call<List<String>>

    /**
     * 查询星座信息
     */
    @GET("constellation/query?key=${AggregateDataApplication.CONSTELLATION_KEY}")
    fun getConstellationInfo(@Query("keyword") keyword: String) : Call<ConstellationResponse>
}