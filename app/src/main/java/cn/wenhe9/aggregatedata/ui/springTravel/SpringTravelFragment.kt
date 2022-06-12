package cn.wenhe9.aggregatedata.ui.springTravel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cn.wenhe9.aggregatedata.AggregateDataApplication
import cn.wenhe9.aggregatedata.R
import cn.wenhe9.aggregatedata.databinding.FragmentSpringTravelBinding
import cn.wenhe9.aggregatedata.logic.model.springTravel.city.City
import cn.wenhe9.aggregatedata.logic.model.springTravel.city.Result
import kotlin.streams.toList

class SpringTravelFragment : Fragment() {
    val viewModel by lazy {
        ViewModelProvider(this).get(SpringTravelViewModel::class.java)
    }

    val param = HashMap<String, String>()

    private var _binding : FragmentSpringTravelBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSpringTravelBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViews()

        queryCities()
    }

    private fun initViews() {
        binding.btnQuery.setOnClickListener {
            queryPolicy()
        }
    }

    private fun queryPolicy() {
        viewModel.setFromAndTo(param)

        viewModel.policyLiveData.observe(viewLifecycleOwner){result ->
            val policy = result.getOrNull()

            if (policy != null){
                showPolicy(policy)
            }else{
                Toast.makeText(AggregateDataApplication.context, "查询政策失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPolicy(policy: cn.wenhe9.aggregatedata.logic.model.springTravel.policy.Result) {
        binding.tvFrom.text = policy.from_info.city_name
        binding.tvFromDesc.text = policy.from_info.out_desc

        binding.viewLine.visibility = View.VISIBLE

        binding.tvTo.text = policy.to_info.city_name
        binding.tvToDesc.text = policy.to_info.out_desc
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
        //设置到达城市的身份 适配器
        setProvincesData(binding.spToProvince, cityList)
        //设置到达城市的城市 适配器
        setCityData(binding.spToCity, cityList[0].citys)
    }

    private fun setProvincesData(spinner: Spinner, cityList: List<Result>) {
        val provinceArray = cityList.stream().map { city -> city.province }.toList()

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(AggregateDataApplication.context, R.layout.item_spinner_dropdown, provinceArray)
        spinner.adapter = adapter
        adapter.notifyDataSetChanged()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (parent?.id) {
                    R.id.sp_province -> setCityData(binding.spCity, cityList[position].citys)
                    R.id.sp_to_province -> setCityData(binding.spToCity, cityList[position].citys)
                    else -> {}
                }
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
                when (parent?.id) {
                    R.id.sp_city -> {
                        binding.tvFrom.text = cityList[position].city
                        param.put("from", cityList[position].city_id)
                    }
                    R.id.sp_to_city -> {
                        binding.tvTo.text = cityList[position].city
                        param.put("to", cityList[position].city_id)
                    }
                    else -> {}
                }
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