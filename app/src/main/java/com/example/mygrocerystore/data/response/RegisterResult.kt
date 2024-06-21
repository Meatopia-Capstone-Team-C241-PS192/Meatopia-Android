package com.example.mygrocerystore.data.response


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterResult(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val address: String? = null,
) : Parcelable