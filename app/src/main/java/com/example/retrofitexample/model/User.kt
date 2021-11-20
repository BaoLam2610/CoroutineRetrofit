package com.example.retrofitexample.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(

//    @SerializedName("username")
//    @Expose
    val username: String,
//    @SerializedName("password")
//    @Expose
    val password: String,
//    @SerializedName("avatar")
//   @Expose
    val avatar: String
) : Serializable