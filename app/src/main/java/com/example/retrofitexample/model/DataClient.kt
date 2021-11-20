package com.example.retrofitexample.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataClient(
    val userId: Int,
    val id: Int,
    val title: String?,
    @SerializedName("body")
    @Expose(deserialize = true)
    val content: String
)
