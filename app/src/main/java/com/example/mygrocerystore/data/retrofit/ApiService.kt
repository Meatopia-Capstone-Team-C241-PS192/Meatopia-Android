package com.example.mygrocerystore.data.retrofit

import com.example.mygrocerystore.data.response.LoginRequest
import com.example.mygrocerystore.data.response.LoginResponse
import com.example.mygrocerystore.data.response.MeatResponseItem
import retrofit2.Response
import retrofit2.http.*
import com.example.mygrocerystore.data.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("meats")
    suspend fun getMeat(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<List<MeatResponseItem>>

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field ("phone") phone: String,
        @Field("address") address: String,
        @Field("password") password: String,
        @Field("password_confirmation") confPassword: String
    ): RegisterResponse

}
