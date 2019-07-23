package com.da62.model

data class Response<T>(val body: T, val statusCode: String, val statusCodeValue: Int)