package com.example.mygrocerystore.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class RegisterResponse(

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("user")
    val user: User? = null
) : Parcelable

@Parcelize
data class User(

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("email")
    val email: String? = null
) : Parcelable
