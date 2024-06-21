package com.example.mygrocerystore.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class MeResponse(
	val password: String? = null,
	val address: String? = null,
	val role: String? = null,
	val phone: String? = null,
	val name: String? = null,
	val id: String? = null,
	val email: String? = null
) : Parcelable
