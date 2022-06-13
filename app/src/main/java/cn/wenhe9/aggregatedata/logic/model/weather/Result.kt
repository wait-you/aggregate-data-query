package cn.wenhe9.aggregatedata.logic.model.weather

data class Result(
    val city: String,
    val future: List<Future>,
    val realtime: Realtime
)