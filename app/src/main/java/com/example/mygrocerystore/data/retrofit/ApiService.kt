package com.example.mygrocerystore.data.retrofit

import com.example.mygrocerystore.data.response.LoginResponse
import com.example.mygrocerystore.data.response.MeatResponse

import com.example.mygrocerystore.data.response.RegisterResponse
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part





interface ApiService {
    @Multipart
    @POST("login")
    suspend fun login(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody
    ): LoginResponse


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


    @GET("meat")
    suspend fun getMeatResponse(): MeatResponse
}
