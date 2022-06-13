package cn.wenhe9.aggregatedata.logic.network

import cn.wenhe9.aggregatedata.AggregateDataApplication
import cn.wenhe9.aggregatedata.logic.model.springTravel.city.CityResponse
import cn.wenhe9.aggregatedata.logic.model.weather.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *@author DuJinliang
 *2022/6/13
 */
interface WeatherService {
    @GET("weather/query?key=${AggregateDataApplication.WEATHER_KEY}")
    fun getWeatherInfo(@Query("city") city: String) : Call<WeatherResponse>
}