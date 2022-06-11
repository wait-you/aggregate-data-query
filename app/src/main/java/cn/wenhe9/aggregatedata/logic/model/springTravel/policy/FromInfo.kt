package cn.wenhe9.aggregatedata.logic.model.springTravel.policy

data class FromInfo(
    val city_id: String,
    val city_name: String,
    val health_code_desc: String,
    val health_code_gid: String,
    val health_code_name: String,
    val health_code_picture: String,
    val health_code_style: String,
    val high_in_desc: String,
    val low_in_desc: String,
    val out_desc: String,
    val province_id: String,
    val province_name: String,
    val risk_level: String
)