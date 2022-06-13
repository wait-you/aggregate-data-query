package cn.wenhe9.aggregatedata

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import cn.wenhe9.aggregatedata.databinding.ActivityMainBinding
import cn.wenhe9.aggregatedata.ui.constellation.ConstellationFragment
import cn.wenhe9.aggregatedata.ui.springTravel.SpringTravelFragment
import cn.wenhe9.aggregatedata.ui.weather.WeatherFragment

class MainActivity : AppCompatActivity() {

    private var weatherFragment: WeatherFragment? = null

    private var springTravelFragment: SpringTravelFragment? = null

    private var constellationFragment: ConstellationFragment? = null

    private lateinit var fragmentTransaction: FragmentTransaction

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSelection(2)
    }

    /**
     * 点击底部导航RadioButton菜单栏后，改变RadioButton图标和文字变成蓝色,
     * 同时加载对应的Fragment页面内容
     *
     * @param index RadioButton在RadioGroup中的序号
     */
    private fun setSelection(index: Int) {
        resetImg()
        //创建FragmentTransaction
        fragmentTransaction = supportFragmentManager.beginTransaction()
        //隐藏所有Fragment
        hideFragments(fragmentTransaction)
        when (index) {
            0 -> {
                weatherFragmentLoad()
            }
            1 -> {
                constellationFragmentLoad()
            }
            2 -> {
                springTravelFragmentLoad()
            }
        }
    }

    //设置点击RadioButton文字和背景颜色并加载FoundFragment
    private fun weatherFragmentLoad() {
        binding.rbFound.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.guide_found_on, 0, 0)
        binding.rbFound.setTextColor(getColor(R.color.lightblue))
        if (springTravelFragment == null){
            springTravelFragment = SpringTravelFragment()
            fragmentTransaction.add(R.id.fg_content, springTravelFragment!!)
        }
        fragmentTransaction.show(springTravelFragment!!)
    }

    //设置点击RadioButton文字和背景颜色并加载MusicFragment
    private fun constellationFragmentLoad() {
        binding.checkwork.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.guide_music_on, 0, 0)
        binding.checkwork.setTextColor(getColor(R.color.lightblue))
        if (constellationFragment == null){
            constellationFragment = ConstellationFragment()
            fragmentTransaction.add(R.id.fg_content, constellationFragment!!)
        }
        fragmentTransaction.show(constellationFragment!!)
    }

    //设置点击RadioButton文字和背景颜色并加载HomePageFragment
    private fun springTravelFragmentLoad() {
        //变换图标
        binding.rbHomePage.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.guide_homepage_on, 0, 0)
        //变换文字颜色
        binding.rbHomePage.setTextColor(getColor(R.color.lightblue))
        if (weatherFragment == null){
            weatherFragment = WeatherFragment()
            fragmentTransaction.add(R.id.fg_content, weatherFragment!!)
        }
        fragmentTransaction.show(weatherFragment!!)
    }

    /**
     * 隐藏所有Fragment
     */
    private fun hideFragments(ft: FragmentTransaction) {
        if (springTravelFragment != null) {
            ft.hide(springTravelFragment!!)
        }
        if (constellationFragment != null) {
            ft.hide(constellationFragment!!)
        }
        if (weatherFragment != null) {
            ft.hide(weatherFragment!!)
        }
        ft.commit()
    }

    /**
     * 恢复所有RadioButton默认颜色和图片
     */
    private fun resetImg() {
        binding.rbHomePage.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.guide_homepage, 0, 0)
        binding.rbHomePage.setTextColor(getColor(R.color.tab_text_bg))
        binding.rbFound.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.guide_found, 0, 0)
        binding.rbFound.setTextColor(getColor(R.color.tab_text_bg))
    }


    fun onSpringTravelClicked(view: View?) {
        setSelection(2)
    }

    fun onConstellationClicked(view: View?) {
        setSelection(1)
    }

    fun onWeatherClicked(view: View?) {
        setSelection(0)
    }

}