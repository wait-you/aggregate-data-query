package cn.wenhe9.aggregatedata.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.wenhe9.aggregatedata.logic.Repository
import cn.wenhe9.aggregatedata.logic.model.weather.Future

/**
 *@author DuJinliang
 *2022/6/13
 * 天气 viewModel
 */
class WeatherViewModel : ViewModel() {
    /**
     * 城市 lvieData
     */
    private val cityLiveData = MutableLiveData<String>()


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
     * 未来天气列表
     */
    var futureList = ArrayList<Future>()

    /**
     * 天气信息 liveData 监听 cityLiveData 更新数据
     */
    val weatherInfoLiveData = Transformations.switchMap(cityLiveData){ city ->
        Repository.getWeatherInfo(city)
    }

    /**
     * 给 cityLiveData 设置值 以触发 weatherInfoLiveData 的监听
     */
    fun getWeatherInfo(city: String) {
        cityLiveData.value = city
    }

    /**
     * 设置 isSavedLiveData 以触发 cityListLiveData 更新
     */
    fun setIsSaved(){
        isSavedLiveData.value = false
    }
}