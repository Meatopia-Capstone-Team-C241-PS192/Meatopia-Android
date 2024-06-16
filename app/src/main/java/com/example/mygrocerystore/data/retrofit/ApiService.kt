package com.example.mygrocerystore.data.retrofit

import com.example.mygrocerystore.data.response.LoginResponse
import com.example.mygrocerystore.data.response.MeatResponse
import okhttp3.RequestBody
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @Multipart
    @POST("login")
    suspend fun login(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody
    ): LoginResponse

    @GET("meat")
    suspend fun getMeatResponse(): MeatResponse
}
