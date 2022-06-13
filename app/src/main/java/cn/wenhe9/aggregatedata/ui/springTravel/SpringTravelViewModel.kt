package cn.wenhe9.aggregatedata.ui.springTravel

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
 * 防疫 viewModel
 */
class SpringTravelViewModel : ViewModel() {
    /**
     * 出发 和 目的地
     */
    private val fromAndToCityLiveData = MutableLiveData<Map<String, String>>()

    /**
     * 是否本地有数据
     */
    private val isSavedLiveData  = MutableLiveData<Boolean>()

    /**
     * 城市列表
     */
    var cityListLiveData = Transformations.switchMap(isSavedLiveData){_ ->
        Repository.getCities()
    }

    /**
     * 监听 fromAndToCityLiveData 更新数据
     */
    val policyLiveData = Transformations.switchMap(fromAndToCityLiveData){ params ->
        Repository.getPolicy(params.getOrDefault("from", ""), params.getOrDefault("to", ""))
    }

    /**
     * 设置 fromAndToCityLiveData 从而触发 policyLiveData 的监听
     */
    fun setFromAndTo(params: Map<String, String>) {
        fromAndToCityLiveData.value = params
    }

    /**
     * 设置 isSavedLiveData 以触发 cityListLiveData 更新
     */
    fun setIsSaved(){
        isSavedLiveData.value = false
    }
}