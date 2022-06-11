package cn.wenhe9.aggregatedata.logic.model.springTravel.policy

data class PolicyResponse(
    val error_code: Int,
    val reason: String,
    val result: Result
)