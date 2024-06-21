package com.example.mygrocerystore.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterResponse(

    @field:SerializedName("registerResult")
    val registerResult: RegisterResultOne? = null,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
) : Parcelable

@Parcelize
data class RegisterResultOne(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("role")
    val role: String
) : Parcelable
