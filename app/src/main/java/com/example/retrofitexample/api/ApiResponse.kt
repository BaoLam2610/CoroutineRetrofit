package com.example.retrofitexample.api

import com.google.gson.annotations.SerializedName

data class ApiResponse (
    @SerializedName("status")
    val status: String,
    @SerializedName("result_code")
    val resultCode: Int,
    @SerializedName("name")
    val name: String
)