package cn.wenhe9.aggregatedata.logic.model.springTravel.policy

data class ToCovidInfo(
    val city_id: String,
    val city_name: String,
    val is_updated: String,
    val today_confirm: String,
    val total_confirm: String,
    val total_dead: String,
    val total_heal: String,
    val updated_at: String
)