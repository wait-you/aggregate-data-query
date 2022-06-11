package cn.wenhe9.aggregatedata.ui.springTravel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.wenhe9.aggregatedata.logic.Repository
import cn.wenhe9.aggregatedata.logic.model.springTravel.City

/**
 *@author DuJinliang
 *2022/6/11
 */
class CityViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<City>()

    val placeLiveData = Transformations.switchMap(searchLiveData){ _ ->
        Repository.getCities()
    }

    fun searchCities(location : String){
        searchLiveData.value = location
    }
}