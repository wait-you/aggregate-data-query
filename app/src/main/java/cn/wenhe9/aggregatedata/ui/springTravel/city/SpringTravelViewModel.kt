package cn.wenhe9.aggregatedata.ui.springTravel.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.wenhe9.aggregatedata.logic.Repository
import cn.wenhe9.aggregatedata.logic.model.springTravel.city.City
import cn.wenhe9.aggregatedata.logic.model.springTravel.city.CityResponse
import cn.wenhe9.aggregatedata.logic.model.springTravel.city.Result

/**
 *@author DuJinliang
 *2022/6/11
 */
class SpringTravelViewModel : ViewModel() {
    private val fromAndToCityLiveData = MutableLiveData<Map<String, String>>()

    var cityList = Repository.getCities()

    val policyLiveData = Transformations.switchMap(fromAndToCityLiveData){ params ->
        Repository.getPolicy(params.getOrDefault("from", ""), params.getOrDefault("to", ""))
    }

    fun setFromAndTo(params: Map<String, String>) {
        fromAndToCityLiveData.value = params
    }
}