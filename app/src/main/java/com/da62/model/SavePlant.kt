package com.da62.model

data class SavePlant(
    val alarm: Boolean = false,
    val card: String,
    val grow: String,
    val kind: String,
    val name: String,
    val raiseDate: String,
    val waterDate: Int,
    val waterTime: String
)