package cn.wenhe9.aggregatedata.ui.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.wenhe9.aggregatedata.AggregateDataApplication
import cn.wenhe9.aggregatedata.R
import cn.wenhe9.aggregatedata.databinding.FragmentWeatherBinding
import cn.wenhe9.aggregatedata.logic.Repository
import cn.wenhe9.aggregatedata.logic.model.springTravel.city.City
import cn.wenhe9.aggregatedata.logic.model.springTravel.city.Result
import kotlin.streams.toList

class WeatherFragment : Fragment() {
    val viewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    /**
     * 城市名称
     */
    var city = ""

    /**
     * 天气 适配器
     */
    private lateinit var adapter: WeatherAdapter

    /**
     * 视图绑定
     */
    private var _binding : FragmentWeatherBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViews()

        queryCities()
    }

    /**
     * 初始化页面
     */
    private fun initViews() {
        //初始化 recyclerView
        val layoutManager = LinearLayoutManager(activity)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = layoutManager
        adapter = WeatherAdapter(viewModel.futureList)
        recyclerView.adapter = adapter

        binding.btnQuery.setOnClickListener {
            queryWeatherInfo()
        }
    }

    /**
     * 查询天气信息
     */
    private fun queryWeatherInfo() {
        viewModel.getWeatherInfo(city)

        viewModel.weatherInfoLiveData.observe(viewLifecycleOwner){result ->
            val weatherInfo = result.getOrNull()

            if (weatherInfo != null){
                showWeather(weatherInfo)
            }else{
                Toast.makeText(AggregateDataApplication.context, "查询天气信息失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 展示天气信息
     */
    private fun showWeather(weatherInfo: cn.wenhe9.aggregatedata.logic.model.weather.Result) {
        binding.weatherContent.visibility = View.VISIBLE
        binding.tvTempeature.text = weatherInfo.realtime.temperature
        binding.tvInfo.text = weatherInfo.realtime.info
        binding.tvDirectPower.text = "${weatherInfo.realtime.direct} -- ${weatherInfo.realtime.power}"

        viewModel.futureList.clear()
        viewModel.futureList.addAll(weatherInfo.future)
        adapter.notifyDataSetChanged()
    }

    /**
     * 查询城市信息
     */
    private fun queryCities() {
//判断是否本地有数据
        if (Repository.isCitiesSaved()){
            val cityList = Repository.getCitiesFromLocal()
            setSpinnerData(cityList)
        }else{
            viewModel.setIsSaved()

            // 监听 cityList 数据变化 执行方法
            viewModel.cityListLiveData.observe(viewLifecycleOwner){ result ->
                val cityList = result.getOrNull()

                if (cityList != null){
                    setSpinnerData(cityList)
                    Repository.saveCities(cityList)
                }else{
                    Toast.makeText(AggregateDataApplication.context, "查询城市信息失败", Toast.LENGTH_SHORT).show()
                    result.exceptionOrNull()?.printStackTrace()
                }
            }
        }
    }

    /**
     * 设置 spinner
     */
    private fun setSpinnerData(cityList: List<Result>) {
        //设置出发城市的省份 适配器
        setProvincesData(binding.spProvince, cityList)
        //设置出发城市的城市 适配器
        setCityData(binding.spCity, cityList[0].citys)
    }

    /**
     * 设置省份数据 和 点击事件
     */
    private fun setProvincesData(spinner: Spinner, cityList: List<Result>) {
        val provinceArray = cityList.stream().map { city -> city.province }.toList()

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(AggregateDataApplication.context, R.layout.item_spinner_dropdown, provinceArray)
        spinner.adapter = adapter
        adapter.notifyDataSetChanged()

        // 设置点击事件, 当点击了省份时, 更新城市
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                setCityData(binding.spCity, cityList[position].citys)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }

    /**
     * 设置城市信息
     */
    private fun setCityData(spinner: Spinner, cityList: List<City>) {
        val cityArray = cityList.stream().map { city -> city.city }.toList()

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(AggregateDataApplication.context, R.layout.item_spinner_dropdown, cityArray)
        spinner.adapter = adapter
        adapter.notifyDataSetChanged()
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                city = cityArray[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

    }

    /**
     * 当销毁时移除binding
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}