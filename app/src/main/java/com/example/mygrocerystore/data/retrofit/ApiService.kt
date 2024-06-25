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
import okhttp3.RequestBody
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

    @Multipart
    @POST("users")
    suspend fun register(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("address") address: RequestBody,
        @Part("password") password: RequestBody,
        @Part("confPassword") confPassword: RequestBody,
        @Part("role") role: RequestBody
    ): Response<RegisterResponse>


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
