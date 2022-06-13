package cn.wenhe9.aggregatedata.logic.model.weather

data class Future(
    val date: String,
    val direct: String,
    val temperature: String,
    val weather: String,
    val wid: Wid
)