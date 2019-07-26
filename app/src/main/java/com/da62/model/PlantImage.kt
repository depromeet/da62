package com.da62.model

import java.util.*

data class PlantImage(
    val id: Int = 0,
    val userId: Int = 0,
    val plantId: Int = 0,
    val imageUrl: String = "",
    val tag: String = "",
    val date: Date = Date()
)