package cn.wenhe9.aggregatedata.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.wenhe9.aggregatedata.R
import cn.wenhe9.aggregatedata.logic.model.weather.Future
import org.w3c.dom.Text

/**
 *@author DuJinliang
 *2022/6/13
 * 天气adapter 用于展示recyclerView的每个条目
 */
class WeatherAdapter(private val futureList: List<Future>): RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val date = view.findViewById<TextView>(R.id.future_date)
        val temperature = view.findViewById<TextView>(R.id.fu_temperature)
        val weather = view.findViewById<TextView>(R.id.fu_weather)
        val direct = view.findViewById<TextView>(R.id.fu_direct)
        val wind = view.findViewById<TextView>(R.id.fu_wind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)

        val holder = ViewHolder(view)

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val future = futureList[position]

        holder.date.text = future.date
        holder.temperature.text = future.temperature
        holder.weather.text = future.weather
        holder.direct.text = future.direct
        holder.wind.text = "${future.wid.day} ~ ${future.wid.night}"
    }

    override fun getItemCount() = futureList.size
}