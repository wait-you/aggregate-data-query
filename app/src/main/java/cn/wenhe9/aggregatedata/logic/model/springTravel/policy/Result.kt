package cn.wenhe9.aggregatedata.logic.model.springTravel.policy

data class Result(
    val f_tag: String,
    val fc_tag: String,
    val from_covid_info: FromCovidInfo,
    val from_info: FromInfo,
    val t_tag: String,
    val tc_tag: String,
    val to_covid_info: ToCovidInfo,
    val to_info: ToInfo
)