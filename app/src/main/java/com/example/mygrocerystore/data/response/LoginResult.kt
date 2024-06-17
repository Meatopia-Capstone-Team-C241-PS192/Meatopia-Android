package com.example.mygrocerystore.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResult (
    val role: String? = null,
    val name: String? = null,
    val id: String? = null,
    val email: String? = null
) : Parcelable
