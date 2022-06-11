package cn.wenhe9.aggregatedata.logic.model.springTravel

data class CityResponse(
    val error_code: Int,
    val reason: String,
    val result: List<Result>
)