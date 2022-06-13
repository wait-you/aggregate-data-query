package cn.wenhe9.aggregatedata.logic.model.weather

data class WeatherResponse(
    val error_code: Int,
    val reason: String,
    val result: Result
)