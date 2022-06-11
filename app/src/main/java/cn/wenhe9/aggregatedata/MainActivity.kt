package cn.wenhe9.aggregatedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.INFO
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import cn.wenhe9.aggregatedata.logic.Repository
import cn.wenhe9.aggregatedata.logic.model.springTravel.CityResponse
import cn.wenhe9.aggregatedata.logic.network.ServiceCreator
import cn.wenhe9.aggregatedata.logic.network.SpringTravelNetwork
import cn.wenhe9.aggregatedata.logic.network.SpringTravelService
import cn.wenhe9.aggregatedata.ui.springTravel.CityViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Level.INFO
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    val viewModel by lazy {
        ViewModelProvider(this).get(CityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.searchCities("1")

        viewModel.placeLiveData.observe(this) { result ->
            val city = result.getOrNull()

            if (city != null) {
                var textView = findViewById<TextView>(R.id.hello)
                textView.text = city.toString()
            } else {
                Toast.makeText(AggregateDataApplication.context, "没有查到数据", Toast.LENGTH_SHORT).show()
            }
        }
    }
}