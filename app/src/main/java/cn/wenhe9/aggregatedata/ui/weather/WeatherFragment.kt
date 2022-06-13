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
import cn.wenhe9.aggregatedata.logic.model.springTravel.city.City
import cn.wenhe9.aggregatedata.logic.model.springTravel.city.Result
import kotlin.streams.toList

class WeatherFragment : Fragment() {
    val viewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    var city = ""

    private lateinit var adapter: WeatherAdapter

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

    private fun initViews() {
        val layoutManager = LinearLayoutManager(activity)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = layoutManager
        adapter = WeatherAdapter(viewModel.futureList)
        recyclerView.adapter = adapter

        binding.btnQuery.setOnClickListener {
            queryWeatherInfo()
        }
    }

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

    private fun showWeather(weatherInfo: cn.wenhe9.aggregatedata.logic.model.weather.Result) {
        binding.weatherContent.visibility = View.VISIBLE
        binding.tvTempeature.text = weatherInfo.realtime.temperature
        binding.tvInfo.text = weatherInfo.realtime.info
        binding.tvDirectPower.text = "${weatherInfo.realtime.direct} -- ${weatherInfo.realtime.power}"

        viewModel.futureList.clear()
        viewModel.futureList.addAll(weatherInfo.future)
        adapter.notifyDataSetChanged()
    }

    private fun queryCities() {
        viewModel.cityList.observe(viewLifecycleOwner){ result ->
            val cityList = result.getOrNull()

            if (cityList != null){
                setSpinnerData(cityList)
            }else{
                Toast.makeText(AggregateDataApplication.context, "查询城市信息失败", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        }
    }

    private fun setSpinnerData(cityList: List<Result>) {
        //设置出发城市的省份 适配器
        setProvincesData(binding.spProvince, cityList)
        //设置出发城市的城市 适配器
        setCityData(binding.spCity, cityList[0].citys)
    }

    private fun setProvincesData(spinner: Spinner, cityList: List<Result>) {
        val provinceArray = cityList.stream().map { city -> city.province }.toList()

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(AggregateDataApplication.context, R.layout.item_spinner_dropdown, provinceArray)
        spinner.adapter = adapter
        adapter.notifyDataSetChanged()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                setCityData(binding.spCity, cityList[position].citys)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

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
                TODO("Not yet implemented")
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}