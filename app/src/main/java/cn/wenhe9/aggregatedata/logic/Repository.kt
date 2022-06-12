package cn.wenhe9.aggregatedata.logic

import androidx.lifecycle.liveData
import cn.wenhe9.aggregatedata.logic.dao.SpringTravelDao
import cn.wenhe9.aggregatedata.logic.model.springTravel.city.City
import cn.wenhe9.aggregatedata.logic.network.AggregateDataNetwork
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 *@author DuJinliang
 *2022/6/11
 */
object Repository {
    fun getConstellationList() = fire(Dispatchers.IO){
        val constellationList = AggregateDataNetwork.getConstellationList()
        if (constellationList != null){
            Result.success(constellationList)
        }else{
            Result.failure(RuntimeException("response is null"))
        }
    }

    fun getConstellationInfo(keyword: String) = fire(Dispatchers.IO){
        val constellationResponse = AggregateDataNetwork.getConstellationInfo(keyword)

        if (constellationResponse.error_code == 0){
            val constellation = constellationResponse.result
            Result.success(constellation)
        }else{
            Result.failure(RuntimeException("response status is ${constellationResponse.error_code}"))
        }
    }

    fun getCities() = fire(Dispatchers.IO){
        val cityResponse = AggregateDataNetwork.getCities()

        if (cityResponse.error_code == 0){
            val cities = cityResponse.result
            Result.success(cities)
        }else{
            Result.failure(RuntimeException("response status is ${cityResponse.error_code}"))
        }
    }

    fun getPolicy(from: String, to: String) = fire(Dispatchers.IO){
        val policyResponse = AggregateDataNetwork.getPolicy(from, to)

        if (policyResponse.error_code == 0){
            val result = policyResponse.result
            Result.success(result)
        }else{
            Result.failure(RuntimeException("response status is ${policyResponse.error_code}"))
        }
    }

    fun savePlace(cities : List<City>) = SpringTravelDao.saveCities(cities)

    private fun <T> fire(context : CoroutineContext, block : suspend () -> Result<T>) = liveData<Result<T>>(context){
        val result =  try {
            block()
        }catch (e : Exception){
            Result.failure<T>(e)
        }
        emit(result)
    }
}