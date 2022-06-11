package cn.wenhe9.aggregatedata.logic.model.springTravel.city

data class CityResponse(
    val error_code: Int,
    val reason: String,
    val result: List<Result>
)