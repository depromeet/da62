package com.da62.model

import java.util.*

data class PlantImageRequest    (
    val date: Date = Date(),
    val image: String = "",
    val plantId: Int = 0,
    val tag: String = "",
    val userId: Int = 0
)