package com.example.retrofitexample.api

import com.example.retrofitexample.model.DataClient
import com.example.retrofitexample.model.PostItem
import com.example.retrofitexample.model.User
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MyAPI {

    // Demo GET
    @Headers
    @GET("posts")
    suspend fun getAllDataClient(

    ): Response<List<DataClient>>

    @GET("posts")   // Query
    suspend fun getAllDataClient(@Query("userId") userId: Int): Response<List<DataClient>>

    @GET("posts")   // Query
    suspend fun getAllDataClient(
        @Query("_sort") sort: String,
        @Query("_order") order: String
    ): Response<List<DataClient>>

    @GET("posts")   // QueryMap
    suspend fun getAllDataClient(
        @QueryMap map: Map<String, String>,
    ): Response<List<DataClient>>

    @GET("posts/{id}/comments") // Path
    suspend fun getPostItems(@Path("id") id: Int): Response<List<PostItem>>


    // Login - Register
    @Multipart
    @POST("upload_image.php")
    suspend fun postAvatar(

        @Part avatar: MultipartBody.Part,
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("register.php")
    fun registerAccount(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("login.php")
    suspend fun loginAccount(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Response<User>

    @FormUrlEncoded
    @POST("login.php")
    suspend fun loginAccount(
        @FieldMap map: Map<String, String>,
    ): Response<User>

    // Demo PUT, PATCH, DELETE
    @PUT("posts/{id}")
    suspend fun putDataClient(
        @Path("id") id: Int,
        @Body data: DataClient,
    ): Response<DataClient>

    @PATCH("posts/{id}")
    suspend fun patchDataClient(
        @Path("id") id: Int,
        @Body data: DataClient,
    ): Response<DataClient>

    @DELETE("posts/{id}")
    suspend fun deleteDataClient(
        @Path("id") id: Int,
    ): Response<Void>

}