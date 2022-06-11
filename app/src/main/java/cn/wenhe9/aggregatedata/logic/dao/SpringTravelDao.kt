package cn.wenhe9.aggregatedata.logic.dao

import android.content.Context
import androidx.core.content.edit
import cn.wenhe9.aggregatedata.AggregateDataApplication
import cn.wenhe9.aggregatedata.logic.model.springTravel.City
import com.google.gson.Gson

/**
 *@author DuJinliang
 *2022/6/10
 */
object SpringTravelDao {
    fun saveCities(cities: List<City>){
        sharedPreferences().edit {
            putString("cities", Gson().toJson(cities))
        }
    }

    private fun sharedPreferences() = AggregateDataApplication.context.getSharedPreferences("aggregation_data", Context.MODE_PRIVATE)
}