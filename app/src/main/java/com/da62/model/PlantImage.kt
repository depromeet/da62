package com.da62.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class PlantImage(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("userId") val userId: Int = 0,
    @SerializedName("plantId") val plantId: Int = 0,
    @SerializedName("imageUrl") val imageUrl: String = "",
    @SerializedName("tag") val tag: String = "",
    @SerializedName("date") val date: Date = Date()
)