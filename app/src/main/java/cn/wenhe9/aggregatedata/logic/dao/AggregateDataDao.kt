package cn.wenhe9.aggregatedata.logic.dao

import android.content.Context
import androidx.core.content.edit
import cn.wenhe9.aggregatedata.AggregateDataApplication
import cn.wenhe9.aggregatedata.logic.model.springTravel.city.City
import cn.wenhe9.aggregatedata.logic.model.springTravel.city.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 *@author DuJinliang
 *2022/6/10
 *将数据保存到本地
 */
object AggregateDataDao {
    /**
     * 将城市信息保存到 sharePreferences
     */
    fun saveCities(cities: List<Result>){
        sharedPreferences().edit {
            putString("cities", Gson().toJson(cities))
        }
    }

    /**
     * 从 sharePreferences 获取城市信息
     */
    fun getSavedCities() : List<Result>{
        val cityJson = sharedPreferences().getString("cities", "")
        return Gson().fromJson(cityJson, genericType<List<Result>>())
    }

    /**
     * 判断 sharePreferences 中是否有数据
     */
    fun isCitiesSaved() = sharedPreferences().contains("place")

    /**
     * 将星座列表村保存 sharePreferences
     */
    fun saveConstellations(constellations: List<String>){
        sharedPreferences().edit {
            putString("constellations", Gson().toJson(constellations))
        }
    }

    /**
     * 从 sharePreferences 获取星座信息
     */
    fun getSavedConstellations(): List<String>{
        val consJson = sharedPreferences().getString("constellations", "")
        return Gson().fromJson(consJson, genericType<List<String>>())
    }

    /**
     * 判断 sharePreferences 中是否有数据
     */
    fun isConsSaved() = sharedPreferences().contains("constellations")

    /**
     * 获取 sharePreferences 对象
     */
    private fun sharedPreferences() = AggregateDataApplication.context.getSharedPreferences("aggregation_data", Context.MODE_PRIVATE)

    /**
     * 使用typeToken获取泛型类型
     */
    inline fun <reified T> genericType() = object: TypeToken<T>() {}.type
}