package com.da62.model

import com.google.gson.annotations.SerializedName
import java.util.*


/**
 *  @param id
 *  @param name - 식물 이름
 *  @param description - 식물 정보
 * */
data class Plant(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("alarm") val alarm: Boolean = false,
    @SerializedName("card") val card: String = "",
    @SerializedName("kind") val kind: String = "",
    @SerializedName("waterTime") val waterTime: String = "",
    @SerializedName("waterDate") val waterDate: Int = 0,
    @SerializedName("love") val love: Int = 0,
    @SerializedName("grow") val grow: String = "",
    @SerializedName("raiseDate") val raiseDate: Date = Date(),
    @SerializedName("plantInfo") val plantInfo: PlantInfo? = null,
    @SerializedName("remainWaterDate") val remainWaterDate: Int = 0,
    @SerializedName("waterCycle") val waterCycle: Int = 0
)