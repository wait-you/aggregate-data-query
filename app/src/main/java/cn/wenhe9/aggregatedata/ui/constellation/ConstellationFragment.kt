package cn.wenhe9.aggregatedata.ui.constellation

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
import cn.wenhe9.aggregatedata.AggregateDataApplication
import cn.wenhe9.aggregatedata.R
import cn.wenhe9.aggregatedata.databinding.FragmentConstellationBinding
import cn.wenhe9.aggregatedata.logic.model.constellation.Result

class ConstellationFragment : Fragment() {
    val viewModel by lazy {
        ViewModelProvider(this).get(ConstellationViewModel::class.java)
    }

    private var _binding : FragmentConstellationBinding? = null

    private val binding get() = _binding!!

    private var constellation = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConstellationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViews()

        queryConstellationList()
    }

    private fun initViews() {
        binding.btnQuery.setOnClickListener {
            queryConstellationInfo()
        }
    }

    private fun queryConstellationInfo() {
        viewModel.getConstellationInfo(constellation)

        viewModel.constellationInfoLiveData.observe(viewLifecycleOwner){result ->
            val constellationInfo = result.getOrNull()

            if (constellationInfo != null){
                showConstellationInfo(constellationInfo)
            }else{
                Toast.makeText(AggregateDataApplication.context, "查询星座信息失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showConstellationInfo(constellationInfo: Result) {
        binding.tvName.text = constellationInfo.name
        binding.viewLine.visibility = View.VISIBLE
        binding.tvRange.text = constellationInfo.range
        binding.viewLine2.visibility = View.VISIBLE
        binding.tvXyhm.text = constellationInfo.xyhm
        binding.viewLine3.visibility = View.VISIBLE
        binding.tvGxmd.text = constellationInfo.gxmd
        binding.viewLine5.visibility = View.VISIBLE
        binding.tvJjtz.text = constellationInfo.jbtz
        binding.viewLine6.visibility = View.VISIBLE
        binding.tvXsfg.text = constellationInfo.xsfg
        binding.viewLine7.visibility = View.VISIBLE
        binding.tvZtpj.text = constellationInfo.zj
    }

    private fun queryConstellationList() {
        viewModel.constellationList.observe(viewLifecycleOwner){ result ->
            val constellationList = result.getOrNull()

            if (constellationList != null){
                setSpinnerData(constellationList)
            }else{
                Toast.makeText(AggregateDataApplication.context, "查询星座列表失败", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        }
    }

    private fun setSpinnerData(constellationList: List<String>) {
        //设置星座列表 适配器
        setConstelllationListData(binding.spCons, constellationList)
    }

    private fun setConstelllationListData(spinner: Spinner, constellationList: List<String>) {

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(AggregateDataApplication.context, R.layout.item_spinner_dropdown, constellationList)
        spinner.adapter = adapter
        adapter.notifyDataSetChanged()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                constellation = constellationList[position]
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