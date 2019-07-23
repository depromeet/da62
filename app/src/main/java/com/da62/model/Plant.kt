package com.da62.model


/**
 *  @param id
 *  @param name - 식물 이름
 *  @param description - 식물 정보
 * */
data class Plant(
    val id: Int = 0,
    val name: String = "",
    val alarm: Boolean = false,
    val card: String = "",
    val kind: String = "",
    val waterTime: String = "",
    val waterDate: Int = 0,
    val love: Int = 0,
    val grow: String = "",
    val raiseDate: String = "",
    val plantInfo: PlantInfo? = null
)