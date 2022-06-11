package cn.wenhe9.aggregatedata.logic.model.springTravel

data class Result(
    val citys: List<City>,
    val province: String,
    val province_id: String
)