package cn.wenhe9.aggregatedata.logic

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.liveData
import cn.wenhe9.aggregatedata.AggregateDataApplication
import cn.wenhe9.aggregatedata.logic.dao.SpringTravelDao
import cn.wenhe9.aggregatedata.logic.model.springTravel.City
import cn.wenhe9.aggregatedata.logic.network.SpringTravelNetwork
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 *@author DuJinliang
 *2022/6/11
 */
object Repository {
    fun getCities() = fire(Dispatchers.IO){
        val cityResponse = SpringTravelNetwork.getCities()

        if (cityResponse.error_code == 0){
            val cities = cityResponse.result
            Result.success(cities)
        }else{
            Result.failure(RuntimeException("response status is ${cityResponse.error_code}"))
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