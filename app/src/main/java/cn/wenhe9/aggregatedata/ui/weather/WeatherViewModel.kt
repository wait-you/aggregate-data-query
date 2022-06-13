package cn.wenhe9.aggregatedata.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.wenhe9.aggregatedata.logic.Repository
import cn.wenhe9.aggregatedata.logic.model.weather.Future

/**
 *@author DuJinliang
 *2022/6/13
 */
class WeatherViewModel : ViewModel() {
    private val cityLiveData = MutableLiveData<String>()

    var cityList = Repository.getCities()

    var futureList = ArrayList<Future>()

    val weatherInfoLiveData = Transformations.switchMap(cityLiveData){ city ->
        Repository.getWeatherInfo(city)
    }

    fun getWeatherInfo(city: String) {
        cityLiveData.value = city
    }
}