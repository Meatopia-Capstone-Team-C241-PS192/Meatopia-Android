package com.example.mygrocerystore.data.retrofit

import com.example.mygrocerystore.data.response.LoginRequest
import com.example.mygrocerystore.data.response.LoginResponse
import com.example.mygrocerystore.data.response.MeResponse
import com.example.mygrocerystore.data.response.MeatResponseItem
import com.example.mygrocerystore.data.response.PredictResponse
import retrofit2.Response
import retrofit2.http.*
import com.example.mygrocerystore.data.response.RegisterResponse
import okhttp3.MultipartBody
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

    @GET("meats/name/{search}")
    suspend fun searchMeat(
        @Header("Authorization") token: String,
        @Path("search") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<MeatResponseItem>

    @FormUrlEncoded
    @POST("users")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("address") address: String,
        @Field("password") password: String,
        @Field("password_confirmation") confPassword: String
    ): RegisterResponse

    @Multipart
    @POST("predict")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): PredictResponse


    @GET("me")
    suspend fun getProfile(
        @Header("Authorization") token: String,
    ): Response<MeResponse>


}
