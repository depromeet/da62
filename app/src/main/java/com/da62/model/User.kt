package com.da62.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("token") @Expose val token: String,
    @SerializedName("userId") @Expose val userId: Int
)