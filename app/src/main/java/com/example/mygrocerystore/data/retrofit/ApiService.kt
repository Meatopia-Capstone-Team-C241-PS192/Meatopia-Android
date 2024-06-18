package com.example.mygrocerystore.data.retrofit

import com.example.mygrocerystore.data.response.LoginRequest
import com.example.mygrocerystore.data.response.LoginResponse
import com.example.mygrocerystore.data.response.MeatResponseItem
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

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

}
