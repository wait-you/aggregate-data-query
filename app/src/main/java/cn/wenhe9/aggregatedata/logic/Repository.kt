package cn.wenhe9.aggregatedata.logic

import androidx.lifecycle.liveData
import cn.wenhe9.aggregatedata.logic.dao.AggregateDataDao
import cn.wenhe9.aggregatedata.logic.model.springTravel.city.City
import cn.wenhe9.aggregatedata.logic.network.AggregateDataNetwork
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 *@author DuJinliang
 *2022/6/11
 * 仓库层，实现对本地数据和网络数据的处理
 */
object Repository {
    /**
     * 从网络查询天气信息
     */
    fun getWeatherInfo(city: String) = fire(Dispatchers.IO){
        val weatherResponse = AggregateDataNetwork.getWeatherInfo(city)
        if (weatherResponse.error_code == 0){
            val weatherInfo = weatherResponse.result
            Result.success(weatherInfo)
        }else{
            Result.failure(RuntimeException("response status is ${weatherResponse.error_code}"))
        }
    }

    /**
     * 从网络查询星座列表
     */
    fun getConstellationList() = fire(Dispatchers.IO){
        val constellationList = AggregateDataNetwork.getConstellationList()
        if (constellationList != null){
            Result.success(constellationList)
        }else{
            Result.failure(RuntimeException("response is null"))
        }
    }

    /**
     * 从网络查询星座信息
     */
    fun getConstellationInfo(keyword: String) = fire(Dispatchers.IO){
        val constellationResponse = AggregateDataNetwork.getConstellationInfo(keyword)

        if (constellationResponse.error_code == 0){
            val constellation = constellationResponse.result
            Result.success(constellation)
        }else{
            Result.failure(RuntimeException("response status is ${constellationResponse.error_code}"))
        }
    }

    /**
     * 从网络查询城市信息
     */
    fun getCities() = fire(Dispatchers.IO){
        val cityResponse = AggregateDataNetwork.getCities()

        if (cityResponse.error_code == 0){
            val cities = cityResponse.result
            Result.success(cities)
        }else{
            Result.failure(RuntimeException("response status is ${cityResponse.error_code}"))
        }
    }

    /**
     * 从网络查询政策信息
     */
    fun getPolicy(from: String, to: String) = fire(Dispatchers.IO){
        val policyResponse = AggregateDataNetwork.getPolicy(from, to)

        if (policyResponse.error_code == 0){
            val result = policyResponse.result
            Result.success(result)
        }else{
            Result.failure(RuntimeException("response status is ${policyResponse.error_code}"))
        }
    }

    /**
     * 将城市信息保存到本地
     */
    fun saveCities(cities : List<cn.wenhe9.aggregatedata.logic.model.springTravel.city.Result>) = AggregateDataDao.saveCities(cities)

    /**
     * 从本地查询城市信息
     */
    fun getCitiesFromLocal() = AggregateDataDao.getSavedCities()

    /**
     * 判断本地是否有城市信息
     */
    fun isCitiesSaved() = AggregateDataDao.isCitiesSaved()

    /**
     * 将星座列表保存到本地
     */
    fun saveConstellations(constellationList : List<String>) = AggregateDataDao.saveConstellations(constellationList)

    /**
     * 从本地查询城市信息
     */
    fun getConstellationFromLocal() = AggregateDataDao.getSavedConstellations()

    /**
     * 判断本地是否有城市信息
     */
    fun isConstellationSaved() = AggregateDataDao.isConsSaved()

    /**
     * 高阶函数
     * 对 retrofit 对结果进行统一的异常判断
     * 并根据不同的数据采用不同的判断方法
     * 最终将数据封装成liveData对象返回
     */
    private fun <T> fire(context : CoroutineContext, block : suspend () -> Result<T>) = liveData<Result<T>>(context){
        val result =  try {
            block()
        }catch (e : Exception){
            Result.failure<T>(e)
        }
        emit(result)
    }
}