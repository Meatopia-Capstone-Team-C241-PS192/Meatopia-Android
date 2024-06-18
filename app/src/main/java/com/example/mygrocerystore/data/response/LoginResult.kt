package com.example.mygrocerystore.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResult (
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val role: String? = null,
    val token: String? = null
) : Parcelable
