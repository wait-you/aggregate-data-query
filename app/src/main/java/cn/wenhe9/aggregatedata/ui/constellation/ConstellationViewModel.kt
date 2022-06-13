package cn.wenhe9.aggregatedata.ui.constellation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.wenhe9.aggregatedata.logic.Repository

/**
 *@author DuJinliang
 *2022/6/12
 * 星座 viewModel
 */
class ConstellationViewModel : ViewModel(){
    /**
     * 星座名称 liveData
     */
    private val constellationLiveData = MutableLiveData<String>()

    /**
     * 是否本地有数据
     */
    private val isSavedLiveData  = MutableLiveData<Boolean>()

    /**
     * 星座列表
     */
    var constellationListLiveData = Transformations.switchMap(isSavedLiveData){_ ->
        Repository.getConstellationList()
    }

    /**
     * 星座信息 liveData 监听 constellationLiveData 数据变化时执行方法更新数据
     */
    val constellationInfoLiveData = Transformations.switchMap(constellationLiveData){ keyword ->
        Repository.getConstellationInfo(keyword)
    }

    /**
     * 给 constellationLiveData 从而 触发 constellationInfoLiveData 的监听
     */
    fun getConstellationInfo(keyword: String) {
        constellationLiveData.value = keyword
    }

    /**
     * 设置 isSavedLiveData 以触发 cityListLiveData 更新
     */
    fun setIsSaved(){
        isSavedLiveData.value = false
    }
}