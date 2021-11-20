package com.example.retrofitexample.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    val gson = GsonBuilder().setLenient().create()
    init {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val builder = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)   // thời gian chờ để đọc data trong lần tiếp theo
            .writeTimeout(5000, TimeUnit.MILLISECONDS)  //
            .connectTimeout(15000, TimeUnit.MILLISECONDS)   //Đặt thời gian chờ kết nối mặc định cho các kết nối mới.

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    companion object {
//        val BASE_URL = "http://192.168.1.4/android/RetrofitExample/"
        val BASE_URL = "https://jsonplaceholder.typicode.com/"

        var retrofit: Retrofit? = null
        var _instance: RetrofitInstance? = null

        fun getInstance() : RetrofitInstance{
            if(_instance == null)
                _instance = RetrofitInstance()
                return _instance!!
        }
    }
    fun getApi(): MyAPI{
        return retrofit!!.create(MyAPI::class.java)
    }
}