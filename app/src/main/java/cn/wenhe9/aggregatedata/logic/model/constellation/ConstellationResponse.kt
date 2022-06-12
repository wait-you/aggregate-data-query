package cn.wenhe9.aggregatedata.logic.model.constellation

data class ConstellationResponse(
    val error_code: Int,
    val reason: String,
    val result: Result
)