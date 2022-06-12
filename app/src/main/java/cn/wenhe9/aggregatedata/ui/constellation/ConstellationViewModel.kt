package cn.wenhe9.aggregatedata.ui.constellation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.wenhe9.aggregatedata.logic.Repository

/**
 *@author DuJinliang
 *2022/6/12
 */
class ConstellationViewModel : ViewModel(){
    private val constellationLiveData = MutableLiveData<String>()

    var constellationList = Repository.getConstellationList()

    val constellationInfoLiveData = Transformations.switchMap(constellationLiveData){ keyword ->
        Repository.getConstellationInfo(keyword)
    }

    fun getConstellationInfo(keyword: String) {
        constellationLiveData.value = keyword
    }
}